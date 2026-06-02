package com.example.youthspace.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.youthspace.repository.BookmarkRepository
import com.example.youthspace.data.BookmarkDummy

class BookmarkViewModel : ViewModel() {

    private val repository = BookmarkRepository()

    var bookmarks =
        mutableStateOf<List<BookmarkDummy>>(emptyList())
        private set

    init {
        bookmarks.value = repository.getBookmarks()
    }
}