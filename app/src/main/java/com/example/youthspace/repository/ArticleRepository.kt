package com.example.youthspace.repository

import com.example.youthspace.data.Article

class ArtikelRepository {

    fun getArticles(): List<Article> {

        return listOf(

            Article(
                id = "1",
                judul = "Mengelola Kecemasan Saat Menghadapi Ujian",
                isi = "Belajar mengatur kecemasan dan menjaga kesehatan mental.",
                kategori_id = "Psikologi"
            ),

            Article(
                id = "2",
                judul = "Panduan Lengkap Mencari Beasiswa",
                isi = "Tips dan langkah mencari beasiswa luar negeri.",
                kategori_id = "Edukasi"
            ),

            Article(
                id = "3",
                judul = "Pentingnya Menjaga Kualitas Tidur",
                isi = "Tidur cukup membantu kesehatan tubuh.",
                kategori_id = "Kesehatan"
            )
        )
    }
}