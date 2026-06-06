package com.example.youthspace.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youthspace.data.User
import com.example.youthspace.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ProfileUpdateState {
    object Idle    : ProfileUpdateState()
    object Loading : ProfileUpdateState()
    object Success : ProfileUpdateState()
    data class Error(val message: String) : ProfileUpdateState()
}

class ProfileViewModel : ViewModel() {

    private val repository = ProfileRepository()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val _updateState = MutableStateFlow<ProfileUpdateState>(ProfileUpdateState.Idle)
    val updateState: StateFlow<ProfileUpdateState> = _updateState

    init {
        loadUser()
    }

    fun loadUser() {
        viewModelScope.launch {
            try {
                _currentUser.value = repository.getCurrentUser()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateName(newName: String) {
        if (newName.isBlank()) {
            _updateState.value = ProfileUpdateState.Error("Nama tidak boleh kosong")
            return
        }
        viewModelScope.launch {
            _updateState.value = ProfileUpdateState.Loading
            try {
                val success = repository.updateUserName(newName.trim())
                if (success) {
                    _currentUser.value = repository.getCurrentUser()
                    _updateState.value = ProfileUpdateState.Success
                } else {
                    _updateState.value = ProfileUpdateState.Error("Gagal update nama")
                }
            } catch (e: Exception) {
                _updateState.value = ProfileUpdateState.Error(e.message ?: "Terjadi kesalahan")
            }
        }
    }

    fun uploadProfilePhoto(context: Context, imageUri: Uri) {
        viewModelScope.launch {
            _updateState.value = ProfileUpdateState.Loading
            try {
                val url = repository.uploadProfilePhoto(context, imageUri)
                if (url != null) {
                    // Refresh data user supaya foto baru langsung tampil
                    _currentUser.value = repository.getCurrentUser()
                    _updateState.value = ProfileUpdateState.Success
                } else {
                    _updateState.value = ProfileUpdateState.Error("Gagal mengupload foto")
                }
            } catch (e: Exception) {
                _updateState.value = ProfileUpdateState.Error(e.message ?: "Terjadi kesalahan")
            }
        }
    }

    fun resetUpdateState() {
        _updateState.value = ProfileUpdateState.Idle
    }
}