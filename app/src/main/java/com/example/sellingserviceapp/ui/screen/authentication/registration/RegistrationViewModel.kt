package com.example.sellingserviceapp.ui.screen.authentication.registration

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sellingserviceapp.data.repository.AuthRepository
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonState
import com.example.sellingserviceapp.ui.screen.authentication.state.TextFieldState
import com.example.sellingserviceapp.util.validateEmail
import com.example.sellingserviceapp.util.validatePasswords
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationViewModel (private val authRepository: AuthRepository): ViewModel() {

    private val _emailState = mutableStateOf(TextFieldState())
    val emailState: State<TextFieldState> = _emailState

    private val _passwordState = mutableStateOf(TextFieldState())
    val passwordState: State<TextFieldState> = _passwordState

    private val _confirmPasswordState = mutableStateOf(TextFieldState())
    val confirmPasswordState: State<TextFieldState> = _confirmPasswordState

    private val _nextButtonState = mutableStateOf(ButtonState())
    val nextButtonState: State<ButtonState> = _nextButtonState

    var showEmailConfirmSheet by mutableStateOf(false)
        private set

    private val confirmCode = "1234"
    private val _emailConfirmCodeState = mutableStateOf(TextFieldState())
    val emailConfirmCodeState: State<TextFieldState> = _emailConfirmCodeState

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
        _emailConfirmCodeState.value= emailConfirmCodeState.value.copy(
            text = code,
            error = ""
        )
        if(_emailConfirmCodeState.value.text == confirmCode) {
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

    fun firstStepRegister() {
        viewModelScope.launch {
            _nextButtonState.value = ButtonState(isClickable = false, isLoading = true)
            val result = authRepository.registerFirstStep(_emailState.value.text, _passwordState.value.text)
            result.onSuccess { response ->
                if (response.isSuccess) {
                    showEmailConfirmSheet = true
                    Log.d("DB_Request", "Успешно")
                } else {
                    showEmailConfirmSheet = false
                    Log.d("DB_Request", "Неудачно")
                }
            }.onFailure { e ->
                Error(e.message ?: "Ошибка регистрации")
                Log.d("DB_Request", "Полный пиздеееец")
            }
            Log.d("DB_Request", "$result")
        }
    }
}

