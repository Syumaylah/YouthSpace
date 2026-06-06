package com.example.youthspace.navigation

sealed class Screen(val route: String) {

    object Login : Screen("login")

    object Register : Screen("register")

    object Dashboard : Screen("dashboard")

    object Beranda : Screen("beranda")

    object Bookmark : Screen("bookmark")

    object Pencarian : Screen("pencarian")

    object Profile : Screen("profile")

    object CreateArticle : Screen("create_article")

    object EditProfile : Screen("edit_profile")

    object Kategori : Screen("kategori")

    object DetailArtikel :
        Screen("detail_artikel/{artikelId}") {

        fun createRoute(artikelId: String) =
            "detail_artikel/$artikelId"
    }

    object ListArtikelByKategori :
        Screen("list_artikel/{kategoriId}") {

        fun createRoute(kategoriId: String) =
            "list_artikel/$kategoriId"
    }
}