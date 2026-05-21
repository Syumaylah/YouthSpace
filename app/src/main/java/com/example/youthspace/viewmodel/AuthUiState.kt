package com.example.youthspace.viewmodel

sealed class AuthUiState {
    object Idle    : AuthUiState()
    object Loading : AuthUiState()
    object Success : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}

sealed class AuthCheckState {
    object Checking        : AuthCheckState()
    object Authenticated   : AuthCheckState()
    object NotAuthenticated: AuthCheckState()
}

sealed class DataUiState<out T> {
    object Idle                        : DataUiState<Nothing>()
    object Loading                     : DataUiState<Nothing>()
    data class Success<T>(val data: T) : DataUiState<T>()
    data class Error(val message: String) : DataUiState<Nothing>()
}
