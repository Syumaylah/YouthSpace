package com.example.youthspace.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youthspace.repository.AuthRepository
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository()
    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState
    private val _authCheckState = MutableStateFlow<AuthCheckState>(AuthCheckState.Checking)
    val authCheckState: StateFlow<AuthCheckState> = _authCheckState
    private val _email           = MutableStateFlow(""); val email: StateFlow<String>           = _email
    private val _password        = MutableStateFlow(""); val password: StateFlow<String>        = _password
    private val _firstName       = MutableStateFlow(""); val firstName: StateFlow<String>       = _firstName
    private val _lastName        = MutableStateFlow(""); val lastName: StateFlow<String>        = _lastName
    private val _username        = MutableStateFlow(""); val username: StateFlow<String>        = _username
    private val _confirmPassword = MutableStateFlow(""); val confirmPassword: StateFlow<String> = _confirmPassword

    init { observeAuthStatus() }

    private fun observeAuthStatus() {
        viewModelScope.launch {
            repository.sessionStatus.collect { status ->
                _authCheckState.value = when (status) {
                    is SessionStatus.Authenticated    -> AuthCheckState.Authenticated
                    is SessionStatus.NotAuthenticated -> AuthCheckState.NotAuthenticated
                    is SessionStatus.Initializing     -> AuthCheckState.Checking
                    is SessionStatus.RefreshFailure   ->
                        if (repository.isLoggedIn()) AuthCheckState.Authenticated
                        else AuthCheckState.NotAuthenticated
                }
            }
        }
    }

    fun onEmailChange(v: String)           { _email.value           = v }
    fun onPasswordChange(v: String)        { _password.value        = v }
    fun onFirstNameChange(v: String)       { _firstName.value       = v }
    fun onLastNameChange(v: String)        { _lastName.value        = v }
    fun onUsernameChange(v: String)        { _username.value        = v }
    fun onConfirmPasswordChange(v: String) { _confirmPassword.value = v }

    fun login() {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                repository.login(_email.value.trim(), _password.value)
                _uiState.value = AuthUiState.Success
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Login gagal.")
            }
        }
    }

    fun register() {
        if (_password.value != _confirmPassword.value) {
            _uiState.value = AuthUiState.Error("Password dan konfirmasi tidak cocok.")
            return
        }
        if (_password.value.length < 8) {
            _uiState.value = AuthUiState.Error("Password minimal 8 karakter.")
            return
        }
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                repository.register(_email.value.trim(), _password.value)
                _uiState.value = AuthUiState.Success
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Registrasi gagal.")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
            _uiState.value = AuthUiState.Idle
            _email.value = ""; _password.value = ""
            _firstName.value = ""; _lastName.value = ""
            _username.value = ""; _confirmPassword.value = ""
        }
    }

    fun resetState() { _uiState.value = AuthUiState.Idle }

    fun currentUserId()    = repository.currentUserId()
    fun currentUserEmail() = repository.currentUserEmail()
}
