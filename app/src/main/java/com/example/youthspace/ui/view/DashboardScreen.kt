package com.example.youthspace.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.NotificationsNone
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
import com.example.youthspace.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = viewModel()
) {

    val articles = viewModel.articles.value

    val categories = listOf(
        "Pengembangan Diri",
        "Edukasi",
        "Psikologi"
    )

    var selectedCategory by remember {
        mutableStateOf(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
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
                        text = "Selamat pagi,",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Reza Pratama",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // SEARCH BAR
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
                        )
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    // KATEGORI
                    Text(
                        text = "Kategori",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyRow {

                        itemsIndexed(categories) { index, item ->

                            val selected = selectedCategory == index

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
                                        selectedCategory = index
                                    }
                                    .padding(
                                        horizontal = 22.dp,
                                        vertical = 12.dp
                                    )
                            ) {

                                Text(
                                    text = item,
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

                    Spacer(modifier = Modifier.height(28.dp))

                    // ARTIKEL PILIHAN
                    Text(
                        text = "Artikel Pilihan",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(Color(0xFF0E4C92))
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    // TERBARU
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = "Terbaru",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )

                        Text(
                            text = "Lihat Semua",
                            color = Color(0xFF1E5AA8),
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            items(articles) { article ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp),

                    shape = RoundedCornerShape(20.dp),

                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {

                    Row(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        // ICON BOX
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
                                text = article.category.uppercase(),
                                color = Color(0xFFC98A21),
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = article.title,
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

                        IconButton(onClick = {}) {

                            Icon(
                                imageVector = Icons.Outlined.BookmarkBorder,
                                contentDescription = null,
                                tint = Color.Gray
                            )
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