package com.example.sellingserviceapp.ui.screen.settings.specialistProfile1

sealed class ScreenState {
    data class Default(val userName: String = "Default", val userRating: String = "Haven't been rated yet")
    object Loading: ScreenState()
    object Success: ScreenState()
    data class Error(val errorMessage: String): ScreenState()
}