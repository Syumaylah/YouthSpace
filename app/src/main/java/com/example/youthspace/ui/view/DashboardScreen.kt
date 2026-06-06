package com.example.youthspace.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.youthspace.navigation.Screen
import com.example.youthspace.viewmodel.DashboardViewModel
import com.example.youthspace.viewmodel.ProfileViewModel
import java.util.Calendar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.material.icons.filled.Bookmark
import com.example.youthspace.viewmodel.BookmarkViewModel

@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: DashboardViewModel = viewModel(),
    profileViewModel: ProfileViewModel = viewModel(),
    bookmarkViewModel: BookmarkViewModel = viewModel()
) {

    val articles = viewModel.articles.value
    val allArticles = viewModel.allArticles.value
    val categories = viewModel.categories.value
    val selectedCategoryIndex = viewModel.selectedCategoryIndex.value
    val showAll = viewModel.showAll.value
    val isLoading = viewModel.isLoading.value

    val featuredArticle = allArticles.firstOrNull()

    val currentUser = profileViewModel.currentUser.value
    val userName = currentUser?.name ?: "Pengguna"

    val greeting = remember {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        when {
            hour < 12 -> "Selamat pagi,"
            hour < 15 -> "Selamat siang,"
            hour < 18 -> "Selamat sore,"
            else      -> "Selamat malam,"
        }
    }

    val bookmarkedIds = bookmarkViewModel.bookmarkedIds.value
    LaunchedEffect(Unit) {
        profileViewModel.loadUser()
        bookmarkViewModel.loadBookmarks()
    }
    LaunchedEffect(Unit) {
        profileViewModel.loadUser()
    }

    Scaffold(

        bottomBar = {

            NavigationBar(
                containerColor = Color.White
            ) {

                NavigationBarItem(
                    selected = true,
                    onClick = { },

                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Home,
                            contentDescription = null
                        )
                    },

                    label = {
                        Text("Beranda")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Screen.Pencarian.route) },

                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = null
                        )
                    },

                    label = {
                        Text("Search")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.navigate(
                            Screen.Bookmark.route
                        )
                    },

                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.BookmarkBorder,
                            contentDescription = null
                        )
                    },

                    label = {
                        Text("Bookmark")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {navController.navigate(Screen.Profile.route) },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text("Profile")
                    }
                )
            }
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF7F7F7))
                .padding(paddingValues)
        ) {

            // TOP BAR
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        modifier = Modifier.size(42.dp),
                        shape = RoundedCornerShape(50),
                        color = Color(0xFFD9D9D9)
                    ) {}

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "YouthSpace",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E5AA8)
                    )
                }

                IconButton(onClick = {}) {

                    Icon(
                        imageVector = Icons.Outlined.NotificationsNone,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {

                item {

                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {

                        Text(
                            text = greeting,
                            color = Color.Gray,
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = userName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // SEARCH BAR
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { navController.navigate(Screen.Pencarian.route) }
                        ) {
                            OutlinedTextField(
                                value = "",
                                onValueChange = {},
                                placeholder = {
                                    Text("Cari artikel...")
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.Search,
                                        contentDescription = null,
                                        tint = Color.Gray
                                    )
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(30.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    focusedBorderColor = Color(0xFFE0E0E0),
                                    unfocusedBorderColor = Color(0xFFE0E0E0)
                                ),
                                enabled = false
                            )
                        }

                        Spacer(modifier = Modifier.height(28.dp))

                        // KATEGORI
                        Text(
                            text = "Kategori",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        if (isLoading && categories.isEmpty()) {
                            // Skeleton loading placeholder
                            Row {
                                repeat(3) {
                                    Box(
                                        modifier = Modifier
                                            .padding(end = 12.dp)
                                            .clip(RoundedCornerShape(30.dp))
                                            .background(Color(0xFFE9EDF5))
                                            .padding(horizontal = 22.dp, vertical = 12.dp)
                                    ) {
                                        Text(
                                            text = "         ",
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                        } else {
                            LazyRow {

                                // Chip "Semua"
                                item {
                                    val selected = selectedCategoryIndex == -1
                                    Box(
                                        modifier = Modifier
                                            .padding(end = 12.dp)
                                            .clip(RoundedCornerShape(30.dp))
                                            .background(
                                                if (selected)
                                                    Color(0xFF0E4C92)
                                                else
                                                    Color(0xFFE9EDF5)
                                            )
                                            .clickable {
                                                viewModel.selectCategory(-1)
                                            }
                                            .padding(
                                                horizontal = 22.dp,
                                                vertical = 12.dp
                                            )
                                    ) {
                                        Text(
                                            text = "Semua",
                                            color = if (selected)
                                                Color.White
                                            else
                                                Color.Gray,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }

                                // Chip per kategori dari DB
                                itemsIndexed(categories) { index, category ->

                                    val selected = selectedCategoryIndex == index

                                    Box(
                                        modifier = Modifier
                                            .padding(end = 12.dp)
                                            .clip(RoundedCornerShape(30.dp))
                                            .background(
                                                if (selected)
                                                    Color(0xFF0E4C92)
                                                else
                                                    Color(0xFFE9EDF5)
                                            )
                                            .clickable {
                                                viewModel.selectCategory(index)
                                            }
                                            .padding(
                                                horizontal = 22.dp,
                                                vertical = 12.dp
                                            )
                                    ) {

                                        Text(
                                            text = category.name,
                                            color = if (selected)
                                                Color.White
                                            else
                                                Color.Gray,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(28.dp))

                        // ARTIKEL PILIHAN
                        Text(
                            text = "Artikel Pilihan",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(170.dp)
                                .clickable {

                                    featuredArticle?.let {
                                        navController.navigate(
                                            Screen.DetailArtikel.createRoute(it.id)
                                        )
                                    }
                                },

                            shape = RoundedCornerShape(18.dp),

                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF0E4C92)
                            )
                        ) {

                            featuredArticle?.let { article ->

                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(20.dp),

                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {

                                    Text(
                                        text = "ARTIKEL PILIHAN",
                                        color = Color.White.copy(alpha = 0.7f),
                                        fontSize = 12.sp
                                    )

                                    Column {

                                        Text(
                                            text = article.judul,
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 22.sp,
                                            lineHeight = 28.sp
                                        )

                                        Spacer(
                                            modifier = Modifier.height(8.dp)
                                        )

                                        Text(
                                            text = article.kategori?.name ?: "",
                                            color = Color.White.copy(alpha = 0.8f),
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }

                            // Jika belum ada artikel, tampilkan placeholder
                            if (featuredArticle == null && isLoading) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(color = Color.White)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(28.dp))

                        // HEADER TERBARU / LIHAT SEMUA
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = if (showAll) "Semua Artikel" else "Terbaru",
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp
                            )

                            Text(
                                text = if (showAll) "Sembunyikan" else "Lihat Semua",
                                color = Color(0xFF1E5AA8),
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.clickable {
                                    viewModel.toggleShowAll()
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }

                if (isLoading && articles.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color(0xFF0E4C92))
                        }
                    }
                } else if (articles.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Tidak ada artikel di kategori ini.",
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                        }
                    }
                } else {
                    items(articles) { article ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 8.dp)
                                .clickable {

                                    navController.navigate(
                                        Screen.DetailArtikel.createRoute(
                                            article.id
                                        )
                                    )
                                }
                        ) {

                            Row(
                                modifier = Modifier.padding(16.dp)
                            ) {

                                // ICON BOX (placeholder jika image_url null)
                                Box(
                                    modifier = Modifier
                                        .size(70.dp)
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(Color(0xFFE8EDFF)),
                                    contentAlignment = Alignment.Center
                                ) {

                                    Text(
                                        text = "✦",
                                        fontSize = 26.sp,
                                        color = Color(0xFF1E5AA8)
                                    )
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {

                                    Text(
                                        text = (article.kategori?.name ?: "").uppercase(),
                                        color = Color(0xFFC98A21),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )

                                    Spacer(modifier = Modifier.height(6.dp))

                                    Text(
                                        text = article.judul,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 18.sp,
                                        lineHeight = 24.sp
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = "8 menit baca",
                                        color = Color.Gray,
                                        fontSize = 13.sp
                                    )
                                }

                                val isBookmarked = bookmarkedIds.contains(article.id)

                                IconButton(onClick = {
                                    bookmarkViewModel.toggleBookmark(article.id)
                                }) {
                                    Icon(
                                        imageVector = if (isBookmarked)
                                            Icons.Filled.Bookmark
                                        else
                                            Icons.Outlined.BookmarkBorder,
                                        contentDescription = null,
                                        tint = if (isBookmarked) Color(0xFF1E5AA8) else Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }
    }
}