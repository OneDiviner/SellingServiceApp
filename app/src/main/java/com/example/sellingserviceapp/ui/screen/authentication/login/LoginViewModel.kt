package com.example.sellingserviceapp.ui.screen.authentication.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonModel
import com.example.sellingserviceapp.ui.screen.authentication.state.TextFieldModel
import com.example.sellingserviceapp.ui.screen.authentication.state.TextFieldState
import com.example.sellingserviceapp.util.extension.validateEmail
import com.example.sellingserviceapp.util.extension.validateFields

class LoginViewModel: ViewModel() {

    var email by mutableStateOf(TextFieldModel(placeholder = "Почта"))
        private set

    var password by mutableStateOf(TextFieldModel(placeholder = "Пароль"))
        private set

    var loginButton by mutableStateOf(ButtonModel(text = "Войти"))
        private set

    fun onEmailChanged(value: String) {
        email = email.copy(value = value)
        email = email.copy(state = TextFieldState.Loading)
        if(email.value.isBlank()) {
            email = email.copy(state = TextFieldState.Error("Empty"))
        } else {
            email = email.copy(state = TextFieldState.Default)
        }
        email = email.copy(state = validateEmail(email.value))
        loginButton = loginButton.copy(state = validateFields())
    }

    fun onPasswordChanged(value: String) {
        password = password.copy(value = value)
        if(password.value.isBlank()) {
            password = password.copy(state = TextFieldState.Error("Empty"))
        } else {
            password = password.copy(state = TextFieldState.Default)
        }
        loginButton = loginButton.copy(state = validateFields())
    }

}