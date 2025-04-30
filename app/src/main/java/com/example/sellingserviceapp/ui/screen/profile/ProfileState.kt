package com.example.sellingserviceapp.ui.screen.profile

sealed class ProfileState {
    data class Default(val userName: String = "Default", val userRating: String = "Haven't been rated yet")
    object Loading: ProfileState()
    object Success: ProfileState()
    data class Error(val errorMessage: String): ProfileState()
}

data class Note(
    val title: String,
    val text: String
)