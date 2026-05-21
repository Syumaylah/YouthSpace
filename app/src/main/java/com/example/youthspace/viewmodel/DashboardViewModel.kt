package com.example.youthspace.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.youthspace.data.Article
import com.example.youthspace.repository.DashboardRepository

class DashboardViewModel : ViewModel() {

    private val repository = DashboardRepository()

    var articles = mutableStateOf(listOf<Article>())
        private set

    init {
        loadArticles()
    }

    private fun loadArticles() {
        articles.value = repository.getArticles()
    }
}