package com.example.youthspace.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youthspace.data.Category
import com.example.youthspace.repository.CategoryRepository
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {

    private val repository = CategoryRepository()

    var categories = mutableStateOf<List<Category>>(emptyList())
        private set

    var isLoading = mutableStateOf(false)
        private set

    var message = mutableStateOf<String?>(null)
        private set

    var inputName = mutableStateOf("")

    var editingCategory = mutableStateOf<Category?>(null)
        private set

    var showDialog = mutableStateOf(false)
        private set

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                categories.value = repository.getCategories()
            } catch (e: Exception) {
                message.value = "Gagal memuat kategori: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun createCategory() {
        val name = inputName.value.trim()
        if (name.isBlank()) {
            message.value = "Nama kategori tidak boleh kosong"
            return
        }
        viewModelScope.launch {
            isLoading.value = true
            try {
                repository.createCategory(name)
                message.value = "Kategori berhasil ditambahkan"
                loadCategories()
                closeDialog()
            } catch (e: Exception) {
                message.value = "Gagal menambahkan kategori: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun updateCategory() {
        val name = inputName.value.trim()
        val category = editingCategory.value ?: return
        if (name.isBlank()) {
            message.value = "Nama kategori tidak boleh kosong"
            return
        }
        viewModelScope.launch {
            isLoading.value = true
            try {
                repository.updateCategory(category.id, name)
                message.value = "Kategori berhasil diperbarui"
                loadCategories()
                closeDialog()
            } catch (e: Exception) {
                message.value = "Gagal memperbarui kategori: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun openAddDialog() {
        editingCategory.value = null
        inputName.value = ""
        showDialog.value = true
    }

    fun closeDialog() {
        showDialog.value = false
        inputName.value = ""
        editingCategory.value = null
    }

    fun saveCategory() {
        if (editingCategory.value == null) {
            createCategory()
        } else {
            updateCategory()
        }
    }
}