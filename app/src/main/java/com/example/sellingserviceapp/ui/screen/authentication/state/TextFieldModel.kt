package com.example.sellingserviceapp.ui.screen.authentication.state

sealed class TextFieldState {
    object Default: TextFieldState()
    object Loading: TextFieldState()
    data class Error(
        val error: String = ""
    ): TextFieldState()
}

data class TextFieldModel(
    val value: String = "",
    val placeholder: String,
    val state: TextFieldState = TextFieldState.Default
)