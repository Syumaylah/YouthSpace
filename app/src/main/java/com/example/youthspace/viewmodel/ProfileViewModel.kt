package com.example.youthspace.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youthspace.data.User
import com.example.youthspace.repository.ProfileRepository
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val repository = ProfileRepository()

    var currentUser = mutableStateOf<User?>(null)
        private set

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            try {
                currentUser.value = repository.getCurrentUser()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}