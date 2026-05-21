package com.example.youthspace.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.youthspace.viewmodel.AuthCheckState
import com.example.youthspace.viewmodel.AuthViewModel
import com.example.youthspace.ui.view.DashboardScreen
import com.example.youthspace.ui.view.LoginScreen
import com.example.youthspace.ui.view.RegisterScreen
import com.example.youthspace.viewmodel.*
import com.example.youthspace.ui.view.RegisterScreen

@Composable
fun AppNavigation(
    authViewModel: AuthViewModel = viewModel()
) {
    val authCheckState = authViewModel.authCheckState.collectAsStateWithLifecycle()

    when (authCheckState.value) {
        is AuthCheckState.Checking -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is AuthCheckState.Authenticated -> {
            MainNavHost(authViewModel, startDestination = Screen.Dashboard.route)
        }
        is AuthCheckState.NotAuthenticated -> {
            MainNavHost(authViewModel, startDestination = Screen.Login.route)
        }
    }
}

@Composable
fun MainNavHost(
    authViewModel: AuthViewModel,
    startDestination: String
) {
    val navController = rememberNavController()

    val email           = authViewModel.email.collectAsStateWithLifecycle()
    val password        = authViewModel.password.collectAsStateWithLifecycle()
    val firstName       = authViewModel.firstName.collectAsStateWithLifecycle()
    val lastName        = authViewModel.lastName.collectAsStateWithLifecycle()
    val username        = authViewModel.username.collectAsStateWithLifecycle()
    val confirmPassword = authViewModel.confirmPassword.collectAsStateWithLifecycle()
    val uiState         = authViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.value) {
        if (uiState.value is AuthUiState.Success) {
            navController.navigate(Screen.Dashboard.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
            authViewModel.resetState()
        }
    }

    NavHost(navController = navController, startDestination = startDestination) {

        composable(Screen.Login.route) {
            LoginScreen(
                email              = email.value,
                password           = password.value,
                uiState            = uiState.value,
                onEmailChange      = authViewModel::onEmailChange,
                onPasswordChange   = authViewModel::onPasswordChange,
                onLoginClick       = { authViewModel.login() },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                firstName               = firstName.value,
                lastName                = lastName.value,
                username                = username.value,
                email                   = email.value,
                password                = password.value,
                confirmPassword         = confirmPassword.value,
                uiState                 = uiState.value,
                onFirstNameChange       = authViewModel::onFirstNameChange,
                onLastNameChange        = authViewModel::onLastNameChange,
                onUsernameChange        = authViewModel::onUsernameChange,
                onEmailChange           = authViewModel::onEmailChange,
                onPasswordChange        = authViewModel::onPasswordChange,
                onConfirmPasswordChange = authViewModel::onConfirmPasswordChange,
                onRegisterClick         = { authViewModel.register() },
                onNavigateToLogin       = { navController.popBackStack() }
            )
        }

        composable(Screen.Dashboard.route) {
            DashboardScreen()
        }
    }
}