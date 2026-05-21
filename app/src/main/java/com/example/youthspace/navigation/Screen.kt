package com.example.youthspace.navigation

sealed class Screen(val route: String) {

    object Login : Screen("login")

    object Register : Screen("register")

    object Dashboard : Screen("Dashboard")

    object Bookmark : Screen("bookmark")

    object Pencarian : Screen("pencarian")

    object Profile : Screen("profile")

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