package com.example.youthspace.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.youthspace.viewmodel.ArticleViewModel
import com.example.youthspace.viewmodel.BookmarkViewModel
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.youthspace.R

@Composable
fun DetailArtikelScreen(
    navController: NavController,
    artikelId: String,
    bookmarkViewModel: BookmarkViewModel = viewModel(),
    articleViewModel: ArticleViewModel = viewModel()
) {
    val bookmarkedIds = bookmarkViewModel.bookmarkedIds.value
    val isBookmarked = bookmarkedIds.contains(artikelId)
    val artikel = articleViewModel.selectedArtikel.value
    val imageRes = when (artikel?.kategori?.name?.lowercase()) {

        "psikologi" -> R.drawable.psychology

        "edukasi" -> R.drawable.education

        "pengembangan diri" -> R.drawable.self_development

        "kesehatan" -> R.drawable.health

        "karir" -> R.drawable.career_tips

        else -> R.drawable.psychology
    }
    val isLoading = articleViewModel.isLoading.value

    LaunchedEffect(artikelId) {
        bookmarkViewModel.loadBookmarks()
        articleViewModel.loadArticleById(artikelId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) {

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = artikel?.judul,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Kembali",
                    tint = Color.White
                )
            }

            IconButton(
                onClick = {
                    bookmarkViewModel.toggleBookmark(artikelId)
                },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = if (isBookmarked)
                        Icons.Filled.Bookmark
                    else
                        Icons.Outlined.BookmarkBorder,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(modifier = Modifier.padding(20.dp)) {

                Text(
                    text = artikel?.kategori?.name?.uppercase() ?: "",
                    color = Color(0xFFC98A21),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = artikel?.judul ?: "",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    lineHeight = 34.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { bookmarkViewModel.toggleBookmark(artikelId) }
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = if (isBookmarked) "Hapus Bookmark" else "Tambah Bookmark",
                            tint = if (isBookmarked) Color(0xFF0E4C92) else Color.Gray
                        )
                    }

                    val wordCount = artikel?.isi?.split(" ")?.size ?: 0
                    val readTime = (wordCount / 200).coerceAtLeast(1)
                    Text("$readTime menit baca")
                }

                Spacer(modifier = Modifier.height(24.dp))

                val paragraphs = artikel?.isi
                    ?.split("\n")
                    ?.map { it.trim() }
                    ?.filter { it.isNotEmpty() }
                    ?: emptyList()

                paragraphs.forEach { paragraph ->
                    Text(
                        text = paragraph,
                        fontSize = 15.sp,
                        lineHeight = 26.sp,
                        color = Color(0xFF222222),
                        textAlign = TextAlign.Justify
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}