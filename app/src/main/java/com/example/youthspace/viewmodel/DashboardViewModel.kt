package com.example.youthspace.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youthspace.data.Artikel
import com.example.youthspace.data.Category
import com.example.youthspace.repository.ArticleRepository
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    private val repository = ArticleRepository()

    var allArticles = mutableStateOf<List<Artikel>>(emptyList())
        private set

    var articles = mutableStateOf<List<Artikel>>(emptyList())
        private set

    var categories = mutableStateOf<List<Category>>(emptyList())
        private set

    var selectedCategoryIndex = mutableStateOf(-1)
        private set

    var showAll = mutableStateOf(false)
        private set

    var isLoading = mutableStateOf(false)
        private set

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val fetchedArticles = repository.getArticles()
                val fetchedCategories = repository.getCategories()
                allArticles.value = fetchedArticles
                categories.value = fetchedCategories
                applyFilter()

                fetchedArticles.forEach {
                    android.util.Log.d("DEBUG", "Artikel: ${it.judul}, kategori: ${it.kategori?.name}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading.value = false
            }
        }
    }

    fun selectCategory(index: Int) {
        selectedCategoryIndex.value = index
        showAll.value = false
        applyFilter()
    }

    fun toggleShowAll() {
        showAll.value = !showAll.value
        applyFilter()
    }

    private fun applyFilter() {
        val catIndex = selectedCategoryIndex.value
        val base = if (catIndex == -1) {
            allArticles.value
        } else {
            val selectedCategory = categories.value.getOrNull(catIndex)
            if (selectedCategory != null) {
                allArticles.value.filter { article ->
                    article.kategoriId == selectedCategory.id
                }
            } else {
                allArticles.value
            }
        }

        articles.value = if (showAll.value) {
            base
        } else {
            base.take(3)
        }
    }

}