package com.example.youthspace.repository

import com.example.youthspace.data.Artikel
import com.example.youthspace.data.Category
import com.example.youthspace.data.SupabaseClientProvider
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order

class ArticleRepository {

    private val client = SupabaseClientProvider.client

    suspend fun getArticles(): List<Artikel> {
        return client.postgrest["artikel"]
            .select(columns = Columns.raw("*, categories!fk_kategori(id, name)")) {
                order("created_at", Order.DESCENDING)
            }
            .decodeList<Artikel>()
    }

    suspend fun getCategories(): List<Category> {
        return client.postgrest["categories"]
            .select()
            .decodeList<Category>()
    }

    suspend fun getArticlesByCategory(categoryId: String): List<Artikel> {
        return client.postgrest["artikel"]
            .select(columns = Columns.raw("*, categories!fk_kategori(id, name)")) {
                filter {
                    eq("kategori_id", categoryId)
                }
                order("created_at", Order.DESCENDING)
            }
            .decodeList<Artikel>()
    }
    suspend fun getArticleById(artikelId: String): Artikel? {
        return try {
            client.postgrest["artikel"]
                .select(columns = Columns.raw("*, categories!fk_kategori(id, name)")) {
                    filter { eq("id", artikelId) }
                }
                .decodeList<Artikel>()
                .firstOrNull()
        } catch (e: Exception) {
            android.util.Log.e("ARTICLE", "getArticleById error: ${e.message}")
            null
        }
    }
}