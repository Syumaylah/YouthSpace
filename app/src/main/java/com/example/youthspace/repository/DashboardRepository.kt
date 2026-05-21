package com.example.youthspace.repository

import com.example.youthspace.data.Article

class DashboardRepository {

    fun getArticles(): List<Article> {

        return listOf(

            Article(
                id = 1,
                title = "Mengelola Kecemasan Saat Menghadapi Ujian Akhir",
                category = "Psikologi",
                content = "Belajar mengatur kecemasan dan menjaga kesehatan mental."
            ),

            Article(
                id = 2,
                title = "Panduan Lengkap Mencari Beasiswa Luar Negeri 2024",
                category = "Edukasi",
                content = "Tips dan langkah mencari beasiswa luar negeri."
            ),

            Article(
                id = 3,
                title = "Pentingnya Menjaga Kualitas Tidur bagi Pertumbuhan",
                category = "Kesehatan",
                content = "Tidur yang cukup membantu kesehatan tubuh dan mental."
            )
        )
    }
}