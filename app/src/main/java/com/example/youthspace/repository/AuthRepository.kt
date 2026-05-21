package com.example.youthspace.repository

import com.example.youthspace.data.SupabaseClientProvider
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.Flow

class AuthRepository {

    private val supabase = SupabaseClientProvider.client

    val sessionStatus: Flow<SessionStatus> = supabase.auth.sessionStatus

    suspend fun register(email: String, password: String) {
        supabase.auth.signUpWith(Email) {
            this.email    = email
            this.password = password
        }
    }

    suspend fun login(email: String, password: String) {
        supabase.auth.signInWith(Email) {
            this.email    = email
            this.password = password
        }
    }

    suspend fun logout() {
        supabase.auth.signOut()
    }

    suspend fun isLoggedIn(): Boolean {
        return try {
            supabase.auth.awaitInitialization()
            supabase.auth.currentSessionOrNull() != null
        } catch (e: Exception) { false }
    }

    fun currentUserId(): String? = supabase.auth.currentUserOrNull()?.id
    fun currentUserEmail(): String? = supabase.auth.currentUserOrNull()?.email
}
