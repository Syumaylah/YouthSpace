package com.example.youthspace.repository

import com.example.youthspace.data.BookmarkDummy

class BookmarkRepository {

    fun getBookmarks(): List<BookmarkDummy> {

        return listOf(

            BookmarkDummy(
                id = "1",
                kategori = "Psikologi",
                judul = "Mengelola Kecemasan di Era Digital",
                tanggal = "25 Apr 2026",
                waktuBaca = "3 menit baca",
                disimpan = "Disimpan kemarin"
            ),

            BookmarkDummy(
                id = "2",
                kategori = "Pengembangan Diri",
                judul = "5 Kebiasaan Remaja yang Mengubah Hidup",
                tanggal = "20 Apr 2026",
                waktuBaca = "5 menit baca",
                disimpan = "9 hari lalu"
            )
        )
    }
}