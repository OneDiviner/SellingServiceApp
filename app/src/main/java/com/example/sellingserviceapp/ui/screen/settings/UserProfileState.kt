package com.example.sellingserviceapp.ui.screen.settings

sealed class UserProfileState {
    data class Default(val userName: String = "Default", val userRating: String = "Haven't been rated yet")
    object Loading: UserProfileState()
    object Success: UserProfileState()
    data class Error(val errorMessage: String): UserProfileState()
}

data class Note(
    val title: String,
    val text: String
)