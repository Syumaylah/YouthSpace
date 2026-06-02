package com.example.youthspace.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.youthspace.data.Article
import com.example.youthspace.repository.ArtikelRepository

class ArticleViewModel : ViewModel() {

    private val repository = ArtikelRepository()

    var articles = mutableStateOf<List<Article>>(emptyList())
        private set

    init {
        loadArticles()
    }

    private fun loadArticles() {
        articles.value = repository.getArticles()
    }
}