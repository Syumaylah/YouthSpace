package com.example.youthspace.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.youthspace.navigation.Screen
import com.example.youthspace.viewmodel.BookmarkViewModel

@Composable
fun BookmarkScreen(
    navController: NavController,
    viewModel: BookmarkViewModel = viewModel()
) {
    val bookmarks = viewModel.bookmarks.value

    LaunchedEffect(Unit) {
        viewModel.loadBookmarks()
    }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Screen.Dashboard.route) },
                    icon = { Icon(Icons.Outlined.Home, contentDescription = null) },
                    label = { Text("Beranda") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Screen.Pencarian.route) },
                    icon = { Icon(Icons.Outlined.Search, contentDescription = null) },
                    label = { Text("Search") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Outlined.BookmarkBorder, contentDescription = null) },
                    label = { Text("Bookmark") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Screen.Profile.route) },
                    icon = { Icon(Icons.Outlined.Person, contentDescription = null) },
                    label = { Text("Profile") }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F6FA))
                .padding(paddingValues)
                .padding(24.dp)
        ) {
            Text(text = "Bookmark", fontSize = 36.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "${bookmarks.size} artikel tersimpan", color = Color.Gray)
            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn {
                items(bookmarks) { bookmark ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 18.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Surface(
                                shape = RoundedCornerShape(30.dp),
                                color = Color(0xFFE8EDF7)
                            ) {
                                Text(
                                    text = bookmark.artikel?.kategori?.name ?: "-",
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    fontSize = 12.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = bookmark.artikel?.judul ?: "-",
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp,
                                color = Color(0xFF173A73)
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Row {
                                Text(text = "8 menit baca", color = Color.Gray)
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(text = "·", color = Color.Gray)
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = bookmark.createdAt?.take(10) ?: "-",
                                    color = Color.Gray
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            HorizontalDivider()
                            Spacer(modifier = Modifier.height(14.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Disimpan", color = Color.Gray)
                                TextButton(
                                    onClick = { viewModel.toggleBookmark(bookmark.artikelId) }
                                ) {
                                    Text("Hapus", color = Color.Red)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}