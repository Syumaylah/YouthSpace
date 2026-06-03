package com.example.youthspace.repository

import com.example.youthspace.data.Bookmark
import com.example.youthspace.data.SupabaseClientProvider
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns

class BookmarkRepository {
    private val client = SupabaseClientProvider.client

    private fun getUserId() = client.auth.currentUserOrNull()?.id ?: ""

    suspend fun getBookmarks(): List<Bookmark> {
        val userId = getUserId()
        if (userId.isEmpty()) return emptyList()
        return try {
            client.postgrest["bookmark"]
                .select(Columns.raw("*, artikel(*, categories(*))")) {
                    filter { eq("user_id", userId) }
                }
                .decodeList<Bookmark>()
        } catch (e: Exception) {
            android.util.Log.e("BOOKMARK", "getBookmarks error: ${e.message}")
            emptyList()
        }
    }

    suspend fun addBookmark(artikelId: String) {
        val userId = getUserId()
        if (userId.isEmpty()) return
        try {
            client.postgrest["bookmark"].insert(
                mapOf("user_id" to userId, "artikel_id" to artikelId)
            )
        } catch (e: Exception) {
            android.util.Log.e("BOOKMARK", "addBookmark error: ${e.message}")
        }
    }

    suspend fun removeBookmark(artikelId: String) {
        val userId = getUserId()
        if (userId.isEmpty()) return
        try {
            client.postgrest["bookmark"].delete {
                filter {
                    eq("user_id", userId)
                    eq("artikel_id", artikelId)
                }
            }
        } catch (e: Exception) {
            android.util.Log.e("BOOKMARK", "removeBookmark error: ${e.message}")
        }
    }

    suspend fun getBookmarkedIds(): Set<String> {
        return getBookmarks().map { it.artikelId }.toSet()
    }
}