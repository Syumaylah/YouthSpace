package com.example.youthspace.repository

import com.example.youthspace.data.Article

class DashboardRepository {

    fun getArticles(): List<Article> {

        return listOf(

            Article(
                id = "1",
                judul = "Mengelola Kecemasan Saat Menghadapi Ujian Akhir",
                kategori_id = "Psikologi",
                isi = "Belajar mengatur kecemasan dan menjaga kesehatan mental."
            ),

            Article(
                id = "2",
                judul = "Panduan Lengkap Mencari Beasiswa Luar Negeri 2024",
                kategori_id = "Edukasi",
                isi = "Tips dan langkah mencari beasiswa luar negeri."
            ),

            Article(
                id = "3",
                judul = "Pentingnya Menjaga Kualitas Tidur bagi Pertumbuhan",
                kategori_id = "Kesehatan",
                isi = "Tidur yang cukup membantu kesehatan tubuh dan mental."
            )
        )
    }
}