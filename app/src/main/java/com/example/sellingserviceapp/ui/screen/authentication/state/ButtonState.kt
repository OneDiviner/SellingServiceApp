package com.example.sellingserviceapp.ui.screen.authentication.state

data class ButtonState (
    val isClickable: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
)