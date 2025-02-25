package com.example.sellingserviceapp.ui.screen.authentication.login

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.sellingserviceapp.ui.screen.authentication.state.TextFieldState
import com.example.sellingserviceapp.util.validateEmail

class LoginViewModel: ViewModel() {

    private val _emailState = mutableStateOf(TextFieldState())
    val emailState: State<TextFieldState> = _emailState

    private val _passwordState = mutableStateOf(TextFieldState())
    val passwordState: State<TextFieldState> = _passwordState

    fun onEmailChanged(email: String) {
        _emailState.value = emailState.value.copy(
            text = email,
            error = validateEmail(email)
        )
    }

    fun onPasswordChanged(password: String) {
        _passwordState.value = passwordState.value.copy(
            text = password,
            error = ""
        )
    }
}