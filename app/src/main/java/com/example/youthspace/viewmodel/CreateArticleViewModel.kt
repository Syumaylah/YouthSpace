package com.example.youthspace.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youthspace.data.Category
import com.example.youthspace.repository.ArticleRepository
import kotlinx.coroutines.launch

class CreateArticleViewModel : ViewModel() {

    private val repository = ArticleRepository()

    var categories = mutableStateOf<List<Category>>(emptyList())
        private set

    var isLoading = mutableStateOf(false)
        private set

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            categories.value = repository.getCategories()
        }
    }

    fun refreshCategories() {
        loadCategories()
    }

    fun createArticle(
        judul: String,
        isi: String,
        kategoriId: String,
        imageUrl: String?,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            isLoading.value = true

            try {
                repository.createArticle(
                    judul = judul,
                    isi = isi,
                    kategoriId = kategoriId,
                    imageUrl = imageUrl
                )

                onSuccess()

            } finally {
                isLoading.value = false
            }
        }
    }
}