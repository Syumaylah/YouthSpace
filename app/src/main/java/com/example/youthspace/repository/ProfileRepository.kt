package com.example.youthspace.repository

import android.net.Uri
import android.content.Context
import com.example.youthspace.data.SupabaseClientProvider
import com.example.youthspace.data.User
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class ProfileRepository {

    private val client = SupabaseClientProvider.client

    fun getCurrentUserId(): String =
        client.auth.currentUserOrNull()?.id ?: ""

    fun getCurrentUserEmail(): String =
        client.auth.currentUserOrNull()?.email ?: ""

    suspend fun getCurrentUser(): User? {
        client.auth.awaitInitialization()
        val userId = getCurrentUserId()
        if (userId.isEmpty()) return null

        return client.postgrest["users"]
            .select { filter { eq("id", userId) } }
            .decodeList<User>()
            .firstOrNull()
    }

    suspend fun updateUserName(newName: String): Boolean {
        val userId = getCurrentUserId()
        if (userId.isEmpty()) return false

        client.postgrest["users"].update(
            update = {
                set("name", newName)
            }
        ) {
            filter { eq("id", userId) }
        }
        return true
    }

    suspend fun uploadProfilePhoto(context: Context, imageUri: Uri): String? {
        val userId = getCurrentUserId()
        if (userId.isEmpty()) return null

        val bytes = context.contentResolver
            .openInputStream(imageUri)
            ?.readBytes()
            ?: return null

        val fileName = "$userId/avatar.jpg"

        client.storage
            .from("avatars")
            .upload(fileName, bytes) {
                upsert = true
            }

        val publicUrl = client.storage
            .from("avatars")
            .publicUrl(fileName)

        client.postgrest["users"].update(
            update = {
                set("photo_url", publicUrl)
            }
        ) {
            filter { eq("id", userId) }
        }

        return publicUrl
    }
}