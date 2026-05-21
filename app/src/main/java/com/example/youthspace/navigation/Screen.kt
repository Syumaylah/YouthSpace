package com.example.youthspace.navigation

sealed class Screen(val route: String) {

    object Dashboard : Screen("dashboard")
}