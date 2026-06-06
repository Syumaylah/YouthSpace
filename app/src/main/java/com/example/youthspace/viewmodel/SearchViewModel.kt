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

        // Debounce: tunggu 600ms lalu search DAN simpan riwayat
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(600)
            performSearchAndSave(query)
        }
    }

    // Dipanggil saat user tekan tombol search di keyboard
    fun onSearchSubmit() {
        val query = searchQuery.value.trim()
        if (query.isBlank()) return
        searchJob?.cancel()
        viewModelScope.launch {
            performSearchAndSave(query)
        }
    }

    // Search + simpan keyword ke riwayat sekaligus
    private suspend fun performSearchAndSave(query: String) {
        isLoading.value = true
        isSearching.value = true
        try {
            searchResults.value = repository.searchArticles(query)
            // Simpan ke riwayat setiap kali search berhasil
            repository.saveKeyword(query.trim())
            loadHistory()
        } catch (e: Exception) {
            errorMessage.value = "Gagal mencari artikel. Coba lagi."
            android.util.Log.e("SEARCH_VM", "performSearchAndSave error: ${e.message}")
        } finally {
            isLoading.value = false
        }
    }

    fun onHistoryClick(keyword: String) {
        searchQuery.value = keyword
        searchJob?.cancel()
        viewModelScope.launch {
            performSearchAndSave(keyword)
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