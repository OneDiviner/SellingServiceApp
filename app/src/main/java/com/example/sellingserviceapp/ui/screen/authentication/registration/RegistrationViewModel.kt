package com.example.sellingserviceapp.ui.screen.authentication.registration

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.ui.screen.authentication.state.TextFieldState
import com.example.sellingserviceapp.util.validateEmail
import com.example.sellingserviceapp.util.validatePasswords
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegistrationViewModel(): ViewModel() {

    private val _emailState = mutableStateOf(TextFieldState())
    val emailState: State<TextFieldState> = _emailState

    private val _passwordState = mutableStateOf(TextFieldState())
    val passwordState: State<TextFieldState> = _passwordState

    private val _confirmPasswordState = mutableStateOf(TextFieldState())
    val confirmPasswordState: State<TextFieldState> = _confirmPasswordState

    var showEmailConfirmSheet by mutableStateOf(false)
        private set

    private val confirmCode = "1234"
    var emailConfirmCode by mutableStateOf("")
        private set

    var isNextButtonEnabled by mutableStateOf(false)
        private set

    var remainingTime by mutableStateOf(60)
        private set

    var isGetNewCodeButtonEnabled by mutableStateOf(false)
        private set

    var isEnteredCodeCorrect by mutableStateOf(false)
        private set

    fun updateTimer() {
        remainingTime = 60
        isGetNewCodeButtonEnabled = false
    }

    suspend fun startGetNewCodeTimer() {
        if (remainingTime > 0) {
            delay(1000L)
            remainingTime -= 1
        } else {
            isGetNewCodeButtonEnabled = true
        }
    }

    fun onEmailChanged(email: String) {
        _emailState.value = emailState.value.copy(
            text = email,
            error = validateEmail(email)
        )
        validateRegistrationFields()
    }

    fun onPasswordChanged(password: String) {
        _passwordState.value = passwordState.value.copy(
            text = password,
            error = ""
        )
        validateRegistrationFields()
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        _confirmPasswordState.value = confirmPasswordState.value.copy(
            text = confirmPassword,
            error = validatePasswords(_passwordState.value.text, confirmPassword)
        )
        validateRegistrationFields()
    }

    fun onEmailConfirmCodeChange(code: String) {
        emailConfirmCode = code
        if(emailConfirmCode == confirmCode) {
            isEnteredCodeCorrect = true
        }
        else {
            isEnteredCodeCorrect = false
        }
    }

    private fun validateRegistrationFields() {
        when {

            emailState.value.text.isBlank() -> isNextButtonEnabled = false
            emailState.value.error.isNotEmpty() -> isNextButtonEnabled = false

            passwordState.value.text.isBlank() -> isNextButtonEnabled = false
            passwordState.value.error.isNotEmpty() -> isNextButtonEnabled = false

            confirmPasswordState.value.text.isBlank() -> isNextButtonEnabled = false
            confirmPasswordState.value.error.isNotEmpty() -> isNextButtonEnabled = false

            else -> isNextButtonEnabled = true
        }
    }

    fun openConfirmEmailSheet() {
        showEmailConfirmSheet = true
    }

    fun closeConfirmEmailSheet() {
        showEmailConfirmSheet = false
    }
}

