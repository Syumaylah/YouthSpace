package com.example.youthspace.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.youthspace.navigation.Screen

@Composable
fun BottomBar(
    navController: NavController
) {

    val items = listOf(
        Triple(Screen.Dashboard.route, "Beranda", Icons.Outlined.Home),
        Triple(Screen.Pencarian.route, "Cari", Icons.Outlined.Search),
        Triple(Screen.Bookmark.route, "Bookmark", Icons.Outlined.BookmarkBorder),
        Triple(Screen.Profile.route, "Profil", Icons.Outlined.PersonOutline)
    )

    NavigationBar {

        val currentRoute =
            navController.currentBackStackEntryAsState().value?.destination?.route

        items.forEach { (route, title, icon) ->

            NavigationBarItem(
                selected = currentRoute == route,

                onClick = {
                    navController.navigate(route) {
                        launchSingleTop = true
                    }
                },

                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = title
                    )
                },

                label = {
                    Text(title)
                }
            )
        }
    }
}