package com.example.youthspace.viewmodel

import androidx.lifecycle.ViewModel
import com.example.youthspace.repository.ProfileRepository

class ProfileViewModel : ViewModel() {
    private val repository = ProfileRepository()
}