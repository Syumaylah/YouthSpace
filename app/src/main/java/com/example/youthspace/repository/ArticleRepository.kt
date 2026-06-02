package com.example.youthspace.repository

import com.example.youthspace.data.Article

class ArticleRepository {

    fun getArticles(): List<Article> {

        return listOf(

            Article(
                id = "1",
                judul = "Mengelola Kecemasan di Era Digital",
                isi = "Belajar mengelola kecemasan di era digital.",
                kategori_id = "Psikologi"
            ),

            Article(
                id = "2",
                judul = "5 Kebiasaan Remaja yang Mengubah Hidup",
                isi = "Kebiasaan kecil yang berdampak besar.",
                kategori_id = "Pengembangan Diri"
            ),

            Article(
                id = "3",
                judul = "Persiapan Karier Sejak Bangku Kuliah",
                isi = "Mulai membangun karier sejak dini.",
                kategori_id = "Karier"
            )
        )
    }
}