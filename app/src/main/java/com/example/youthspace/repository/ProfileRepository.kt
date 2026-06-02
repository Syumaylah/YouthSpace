package com.example.youthspace.repository

import com.example.youthspace.data.SupabaseClientProvider
import com.example.youthspace.data.User
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest

class ProfileRepository {

    private val client = SupabaseClientProvider.client

    fun getCurrentUserId(): String {
        return client.auth.currentUserOrNull()?.id ?: ""
    }

    fun getCurrentUserEmail(): String {
        return client.auth.currentUserOrNull()?.email ?: ""
    }

    suspend fun getCurrentUser(): User? {
        val userId = getCurrentUserId()
        if (userId.isEmpty()) return null
        return client.postgrest["users"]
            .select {
                filter {
                    eq("id", userId)
                }
            }
            .decodeList<User>()
            .firstOrNull()
    }
}