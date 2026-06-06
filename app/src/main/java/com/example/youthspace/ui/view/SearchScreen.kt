package com.example.youthspace.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.youthspace.navigation.Screen
import com.example.youthspace.ui.theme.*
import com.example.youthspace.viewmodel.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = viewModel()
) {
    val searchQuery    = viewModel.searchQuery.value
    val searchResults  = viewModel.searchResults.value
    val searchHistory  = viewModel.searchHistory.value
    val isLoading      = viewModel.isLoading.value
    val isSearching    = viewModel.isSearching.value
    val errorMessage   = viewModel.errorMessage.value

    val focusManager   = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        viewModel.loadHistory()
    }

    Scaffold(
        containerColor = Color(0xFFF5F6FA),
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Screen.Dashboard.route) },
                    icon = { Icon(Icons.Outlined.Home, contentDescription = null) },
                    label = { Text("Beranda") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Outlined.Search, contentDescription = null) },
                    label = { Text("Search") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = YSBlueButton,
                        selectedTextColor = YSBlueButton,
                        indicatorColor = YSChipBg
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Screen.Bookmark.route) },
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
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text(
                text = "Pencarian",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = YSTextDark
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onQueryChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                placeholder = {
                    Text("Ketik kata kunci...", color = YSTextLight)
                },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Search,
                        contentDescription = null,
                        tint = YSTextLight
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = {
                            viewModel.clearSearch()
                            focusManager.clearFocus()
                        }) {
                            Icon(Icons.Outlined.Close, contentDescription = "Hapus", tint = YSTextLight)
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = YSBlueButton,
                    unfocusedBorderColor = YSBorderGray,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        viewModel.onSearchSubmit()
                        focusManager.clearFocus()
                    }
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            when {
                isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = YSBlueButton)
                    }
                }

                errorMessage != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(errorMessage, color = YSErrorRed, fontSize = 14.sp)
                    }
                }

                isSearching -> {
                    SearchResultsContent(
                        query = searchQuery,
                        results = searchResults,
                        onArticleClick = { artikelId ->
                            navController.navigate(Screen.DetailArtikel.createRoute(artikelId))
                        }
                    )
                }

                else -> {
                    SearchHistoryContent(
                        history = searchHistory,
                        onHistoryClick = { keyword ->
                            viewModel.onHistoryClick(keyword)
                            focusManager.clearFocus()
                        },
                        onDeleteItem = { id -> viewModel.deleteHistoryItem(id) },
                        onClearAll = { viewModel.clearAllHistory() }
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchHistoryContent(
    history: List<com.example.youthspace.data.SearchHistory>,
    onHistoryClick: (String) -> Unit,
    onDeleteItem: (String) -> Unit,
    onClearAll: () -> Unit
) {
    if (history.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Outlined.History,
                    contentDescription = null,
                    tint = YSTextLight,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "Belum ada riwayat pencarian",
                    color = YSTextLight,
                    fontSize = 14.sp
                )
            }
        }
        return
    }

    LazyColumn {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "RIWAYAT PENCARIAN",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = YSTextLight,
                    letterSpacing = 0.8.sp
                )
                TextButton(onClick = onClearAll) {
                    Text(
                        text = "HAPUS SEMUA",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = YSBlueButton
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }

        items(history, key = { it.id }) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onHistoryClick(item.keyword) }
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Outlined.History,
                    contentDescription = null,
                    tint = YSTextLight,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = item.keyword,
                    fontSize = 15.sp,
                    color = YSTextDark,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = { onDeleteItem(item.id) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Outlined.Close,
                        contentDescription = "Hapus",
                        tint = YSTextLight,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            HorizontalDivider(color = YSBorderLight)
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Jelajahi Topik Terpopuler",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = YSTextDark
            )
            Spacer(modifier = Modifier.height(12.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(popularTopics) { topic ->
                    TopicChip(
                        label = topic,
                        onClick = { onHistoryClick(topic) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

private val popularTopics = listOf(
    "Kesehatan Mental", "Tips Belajar", "Karir", "Psikologi", "Pengembangan Diri"
)

@Composable
private fun TopicChip(label: String, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = YSChipBg,
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            fontSize = 13.sp,
            color = YSBlueButton,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun SearchResultsContent(
    query: String,
    results: List<com.example.youthspace.data.Artikel>,
    onArticleClick: (String) -> Unit
) {
    if (results.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Outlined.SearchOff,
                    contentDescription = null,
                    tint = YSTextLight,
                    modifier = Modifier.size(52.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "Tidak ada hasil untuk \"$query\"",
                    color = YSTextLight,
                    fontSize = 14.sp
                )
            }
        }
        return
    }

    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text(
                text = "${results.size} hasil untuk \"$query\"",
                fontSize = 13.sp,
                color = YSTextLight
            )
            Spacer(modifier = Modifier.height(4.dp))
        }

        items(results, key = { it.id }) { artikel ->
            ArticleResultCard(
                artikel = artikel,
                onClick = { onArticleClick(artikel.id) }
            )
        }
    }
}

@Composable
private fun ArticleResultCard(
    artikel: com.example.youthspace.data.Artikel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!artikel.imageUrl.isNullOrBlank()) {
                AsyncImage(
                    model = artikel.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(YSChipBg)
                )
                Spacer(modifier = Modifier.width(14.dp))
            } else {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(YSChipBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.Article,
                        contentDescription = null,
                        tint = YSBlueButton,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
            }

            Column(modifier = Modifier.weight(1f)) {
                // Badge kategori
                artikel.kategori?.let { cat ->
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = YSChipBg
                    ) {
                        Text(
                            text = cat.name.uppercase(),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = YSBlueButton
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                }

                Text(
                    text = artikel.judul,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = YSNavyText,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = artikel.isi,
                    fontSize = 12.sp,
                    color = YSTextLight,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon(
                Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = YSTextLight,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}