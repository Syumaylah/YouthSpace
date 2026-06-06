package com.example.youthspace.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youthspace.data.Artikel
import com.example.youthspace.data.SearchHistory
import com.example.youthspace.repository.SearchHistoryRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val repository = SearchHistoryRepository()

    var searchQuery = mutableStateOf("")
        private set

    var searchResults = mutableStateOf<List<Artikel>>(emptyList())
        private set

    var searchHistory = mutableStateOf<List<SearchHistory>>(emptyList())
        private set

    var isLoading = mutableStateOf(false)
        private set

    var isSearching = mutableStateOf(false)
        private set

    // Error message
    var errorMessage = mutableStateOf<String?>(null)
        private set

    private var searchJob: Job? = null

    init {
        loadHistory()
    }

    fun loadHistory() {
        viewModelScope.launch {
            try {
                searchHistory.value = repository.getSearchHistory()
            } catch (e: Exception) {
                android.util.Log.e("SEARCH_VM", "loadHistory error: ${e.message}")
            }
        }
    }

    fun onQueryChange(query: String) {
        searchQuery.value = query
        errorMessage.value = null

        if (query.isBlank()) {
            isSearching.value = false
            searchResults.value = emptyList()
            searchJob?.cancel()
            return
        }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(400)
            performSearch(query)
        }
    }

    fun onSearchSubmit() {
        val query = searchQuery.value.trim()
        if (query.isBlank()) return
        searchJob?.cancel()
        viewModelScope.launch {
            performSearch(query)
            repository.saveKeyword(query)
            loadHistory()
        }
    }

    private suspend fun performSearch(query: String) {
        isLoading.value = true
        isSearching.value = true
        try {
            searchResults.value = repository.searchArticles(query)
        } catch (e: Exception) {
            errorMessage.value = "Gagal mencari artikel. Coba lagi."
            android.util.Log.e("SEARCH_VM", "performSearch error: ${e.message}")
        } finally {
            isLoading.value = false
        }
    }

    fun onHistoryClick(keyword: String) {
        searchQuery.value = keyword
        searchJob?.cancel()
        viewModelScope.launch {
            performSearch(keyword)
            // Pindahkan ke atas dengan re-save
            repository.saveKeyword(keyword)
            loadHistory()
        }
    }

    fun deleteHistoryItem(id: String) {
        viewModelScope.launch {
            repository.deleteKeyword(id)
            searchHistory.value = searchHistory.value.filter { it.id != id }
        }
    }

    fun clearAllHistory() {
        viewModelScope.launch {
            repository.clearAllHistory()
            searchHistory.value = emptyList()
        }
    }

    fun clearSearch() {
        searchQuery.value = ""
        searchResults.value = emptyList()
        isSearching.value = false
        errorMessage.value = null
        searchJob?.cancel()
    }
}