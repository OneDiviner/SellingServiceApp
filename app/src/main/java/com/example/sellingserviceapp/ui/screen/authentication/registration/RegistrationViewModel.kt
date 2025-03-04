package com.example.sellingserviceapp.ui.screen.authentication.registration

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sellingserviceapp.data.model.AuthApiError
import com.example.sellingserviceapp.data.model.response.UsersFirstStepRegisterResponse
import com.example.sellingserviceapp.data.repository.AuthRepository
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonState
import com.example.sellingserviceapp.ui.screen.authentication.state.FirstStepRegisterUiState
import com.example.sellingserviceapp.ui.screen.authentication.state.TextFieldState
import com.example.sellingserviceapp.util.validateEmail
import com.example.sellingserviceapp.util.validatePasswords
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _emailState = mutableStateOf(TextFieldState())
    val emailState: State<TextFieldState> = _emailState

    private val _passwordState = mutableStateOf(TextFieldState())
    val passwordState: State<TextFieldState> = _passwordState

    private val _confirmPasswordState = mutableStateOf(TextFieldState())
    val confirmPasswordState: State<TextFieldState> = _confirmPasswordState

    private val _nextButtonState = mutableStateOf<ButtonState>(ButtonState.Default("Продолжить", false))
    val nextButtonState: State<ButtonState> = _nextButtonState

    var showEmailConfirmSheet by mutableStateOf(false)
        private set

    private val confirmCode = "1234"
    private val _emailConfirmCodeState = mutableStateOf(TextFieldState())
    val emailConfirmCodeState: State<TextFieldState> = _emailConfirmCodeState

    var remainingTime by mutableStateOf(60)
        private set

    var isGetNewCodeButtonEnabled by mutableStateOf(false)
        private set

    var isEnteredCodeCorrect by mutableStateOf(false)
        private set

    /*private val _firstStepRegisterUiState = MutableLiveData<FirstStepRegisterUiState>()
    val firstStepRegisterUiState: LiveData<FirstStepRegisterUiState> = _firstStepRegisterUiState*/

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
        if(_emailConfirmCodeState.value.text == code) {
            isEnteredCodeCorrect = true
        }
        else {
            isEnteredCodeCorrect = false
        }
    }

    private fun validateRegistrationFields() {
        when {

            emailState.value.text.isBlank() -> _nextButtonState.value = ButtonState.Default("Продолжить", false)
            emailState.value.error.isNotEmpty() -> _nextButtonState.value = ButtonState.Default("Продолжить", false)

            passwordState.value.text.isBlank() -> _nextButtonState.value = ButtonState.Default("Продолжить", false)
            passwordState.value.error.isNotEmpty() -> _nextButtonState.value = ButtonState.Default("Продолжить", false)

            confirmPasswordState.value.text.isBlank() -> _nextButtonState.value = ButtonState.Default("Продолжить", false)
            confirmPasswordState.value.error.isNotEmpty() -> _nextButtonState.value = ButtonState.Default("Продолжить", false)

            else -> _nextButtonState.value = ButtonState.Default("Продолжить", true)
        }
    }

    fun openConfirmEmailSheet() {
        showEmailConfirmSheet = true
    }

    fun closeConfirmEmailSheet() {
        showEmailConfirmSheet = false
    }

    fun createVerificationEmail(email: String) {
        viewModelScope.launch {
            val result = authRepository.createVerificationEmail(email)
            result.onSuccess { response ->
                showEmailConfirmSheet = true
            }.onFailure { error ->

            }
        }
    }

    fun userFirstStepRegister(email: String, password: String) {
        viewModelScope.launch {
            _nextButtonState.value = ButtonState.Loading
            val result = authRepository.firstStepRegister(email, password)

            result.onSuccess { response ->
                _nextButtonState.value = ButtonState.Default("Продолжить", isClickable = true)
                createVerificationEmail(email)
                // Обработка успешной регистрации запросить код потом отправить на проверку
            }.onFailure { error ->
                _nextButtonState.value = ButtonState.Error(error = error.message)
                // запросить статус пользователя
            }

        }
    }

    //TODO: Исправить состояния кнопки, разобраться с Exception, не забыть поменять IP на x.x.x.190, Extentions Object
    // 200 OK 201 создан 409 конфликт(Что такое поле уже есть)
}

// Сначала создать код @PostMapping("/public/create-verification-email")
// потом проверка  @PatchMapping("/public/verification-email")