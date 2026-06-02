package com.example.youthspace.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String       = "",
    val name: String     = "",
    val email: String    = "",
    val password: String = "",
    @SerialName("photo_url")
    val photoUrl: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null
)

@Serializable
data class Category(
    val id: String   = "",
    val name: String = ""
)

@Serializable
data class Artikel(
    val id: String          = "",
    val judul: String       = "",
    val isi: String         = "",
    @SerialName("kategori_id")
    val kategoriId: String  = "",
    @SerialName("created_at")
    val createdAt: String?  = null,
    @SerialName("image_url")
    val imageUrl: String?   = null,
    @SerialName("categories")
    val kategori: Category? = null
)

@Serializable
data class Bookmark(
    val id: String          = "",
    @SerialName("user_id")
    val userId: String      = "",
    @SerialName("artikel_id")
    val artikelId: String   = "",
    @SerialName("created_at")
    val createdAt: String?  = null,
    val artikel: Artikel?   = null
)

@Serializable
data class SearchHistory(
    val id: String          = "",
    @SerialName("user_id")
    val userId: String      = "",
    val keyword: String     = "",
    @SerialName("created_at")
    val createdAt: String?  = null
)
