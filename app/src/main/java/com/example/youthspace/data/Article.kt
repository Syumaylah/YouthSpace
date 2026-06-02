package com.example.youthspace.data

import kotlinx.serialization.Serializable

@Serializable
data class Article(

    val id: String = "",

    val judul: String = "",

    val isi: String = "",

    val kategori_id: String = "",

    val image_url: String? = null,

    val created_at: String? = null
)