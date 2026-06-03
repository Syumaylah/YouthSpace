package com.example.youthspace.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youthspace.data.Bookmark
import com.example.youthspace.repository.BookmarkRepository
import kotlinx.coroutines.launch

class BookmarkViewModel : ViewModel() {
    private val repository = BookmarkRepository()

    var bookmarks = mutableStateOf<List<Bookmark>>(emptyList())
        private set

    var bookmarkedIds = mutableStateOf<Set<String>>(emptySet())
        private set

    init { loadBookmarks() }

    fun loadBookmarks() {
        viewModelScope.launch {
            bookmarks.value = repository.getBookmarks()
            bookmarkedIds.value = bookmarks.value.map { it.artikelId }.toSet()
        }
    }

    fun toggleBookmark(artikelId: String) {
        viewModelScope.launch {
            if (bookmarkedIds.value.contains(artikelId)) {
                repository.removeBookmark(artikelId)
                bookmarkedIds.value = bookmarkedIds.value - artikelId
            } else {
                repository.addBookmark(artikelId)
                bookmarkedIds.value = bookmarkedIds.value + artikelId
            }
            loadBookmarks()
        }
    }
}