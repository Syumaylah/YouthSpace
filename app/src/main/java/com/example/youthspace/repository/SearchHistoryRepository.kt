package com.example.youthspace.repository

import com.example.youthspace.data.Artikel
import com.example.youthspace.data.SearchHistory
import com.example.youthspace.data.SupabaseClientProvider
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order

class SearchHistoryRepository {

    private val client = SupabaseClientProvider.client

    private fun getUserId() = client.auth.currentUserOrNull()?.id ?: ""

    suspend fun saveKeyword(keyword: String) {
        val userId = getUserId()
        if (userId.isEmpty() || keyword.isBlank()) return
        try {
            client.postgrest["search_history"].delete {
                filter {
                    eq("user_id", userId)
                    eq("keyword", keyword.trim())
                }
            }
            // Insert keyword baru (agar paling atas)
            client.postgrest["search_history"].insert(
                mapOf(
                    "user_id" to userId,
                    "keyword" to keyword.trim()
                )
            )
        } catch (e: Exception) {
            android.util.Log.e("SEARCH_REPO", "saveKeyword error: ${e.message}")
        }
    }

    suspend fun getSearchHistory(): List<SearchHistory> {
        val userId = getUserId()
        if (userId.isEmpty()) return emptyList()
        return try {
            client.postgrest["search_history"]
                .select {
                    filter { eq("user_id", userId) }
                    order("created_at", Order.DESCENDING)
                    limit(10)
                }
                .decodeList<SearchHistory>()
        } catch (e: Exception) {
            android.util.Log.e("SEARCH_REPO", "getSearchHistory error: ${e.message}")
            emptyList()
        }
    }

    suspend fun deleteKeyword(id: String) {
        try {
            client.postgrest["search_history"].delete {
                filter { eq("id", id) }
            }
        } catch (e: Exception) {
            android.util.Log.e("SEARCH_REPO", "deleteKeyword error: ${e.message}")
        }
    }

    suspend fun clearAllHistory() {
        val userId = getUserId()
        if (userId.isEmpty()) return
        try {
            client.postgrest["search_history"].delete {
                filter { eq("user_id", userId) }
            }
        } catch (e: Exception) {
            android.util.Log.e("SEARCH_REPO", "clearAllHistory error: ${e.message}")
        }
    }

    suspend fun searchArticles(keyword: String): List<Artikel> {
        if (keyword.isBlank()) return emptyList()
        return try {
            client.postgrest["artikel"]
                .select(columns = Columns.raw("*, categories!fk_kategori(id, name)")) {
                    filter {
                        or {
                            ilike("judul", "%${keyword.trim()}%")
                            ilike("isi", "%${keyword.trim()}%")
                        }
                    }
                    order("created_at", Order.DESCENDING)
                }
                .decodeList<Artikel>()
        } catch (e: Exception) {
            android.util.Log.e("SEARCH_REPO", "searchArticles error: ${e.message}")
            emptyList()
        }
    }
}