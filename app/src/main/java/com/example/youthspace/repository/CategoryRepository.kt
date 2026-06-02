package com.example.youthspace.repository


import com.example.youthspace.data.Category
import com.example.youthspace.data.SupabaseClientProvider
import io.github.jan.supabase.postgrest.postgrest

class CategoryRepository {

    private val client = SupabaseClientProvider.client

    suspend fun getCategories(): List<Category> {
        return client.postgrest["categories"]
            .select()
            .decodeList<Category>()
    }

    suspend fun createCategory(name: String) {
        val newCategory = Category(name = name)
        client.postgrest["categories"]
            .insert(newCategory)
    }

    suspend fun updateCategory(id: String, newName: String) {
        client.postgrest["categories"]
            .update(
                {
                    set("name", newName)
                }
            ) {
                filter {
                    eq("id", id)
                }
            }
    }

    suspend fun deleteCategory(id: String) {
        client.postgrest["categories"]
            .delete {
                filter {
                    eq("id", id)
                }
            }
    }
}