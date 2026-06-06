package com.example.youthspace.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.youthspace.data.Artikel
import com.example.youthspace.repository.ArticleRepository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ArticleViewModel : ViewModel() {
    private val repository = ArticleRepository()
    var articles = mutableStateOf<List<Artikel>>(emptyList())
        private set

    var selectedArtikel = mutableStateOf<Artikel?>(null)
        private set

    var isLoading = mutableStateOf(false)
        private set

    init {
        loadArticles()
    }

    private fun loadArticles() {
        viewModelScope.launch {
            articles.value = repository.getArticles()
        }
    }

    fun loadArticleById(artikelId: String) {
        viewModelScope.launch {
            isLoading.value = true
            selectedArtikel.value = repository.getArticleById(artikelId)
            isLoading.value = false
        }
    }
}