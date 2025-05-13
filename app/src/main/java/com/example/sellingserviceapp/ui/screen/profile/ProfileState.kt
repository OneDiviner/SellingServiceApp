package com.example.sellingserviceapp.ui.screen.profile

sealed class ProfileSheetState {
    object Profile: ProfileSheetState()
    object EditProfile: ProfileSheetState()
}

data class Note(
    val title: String,
    val text: String
)