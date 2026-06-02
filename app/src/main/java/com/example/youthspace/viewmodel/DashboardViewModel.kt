package com.example.youthspace.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youthspace.data.Article
import com.example.youthspace.repository.ArticleRepository
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    private val repository = ArticleRepository()

    var articles = mutableStateOf<List<Article>>(emptyList())
        private set

    init {
        loadArticles()
    }

    private fun loadArticles() {

        viewModelScope.launch {

            try {

                articles.value =
                    repository.getArticles()

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }
}