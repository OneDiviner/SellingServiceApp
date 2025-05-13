package com.example.sellingserviceapp.ui.screen.authentication.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.di.GlobalAppState
import com.example.sellingserviceapp.data.di.SecureTokenStorage
import com.example.sellingserviceapp.data.local.UserDataStorage
import com.example.sellingserviceapp.data.network.authorization.repository.AuthRepository
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonModel
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonState
import com.example.sellingserviceapp.ui.screen.authentication.state.TextFieldModel
import com.example.sellingserviceapp.ui.screen.authentication.state.TextFieldState
import com.example.sellingserviceapp.util.extension.validateEmail
import com.example.sellingserviceapp.util.extension.validateFields
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val globalAppState: GlobalAppState,
    private val userDataStorage: UserDataStorage,
    private val secureTokenStorage: SecureTokenStorage
): ViewModel() {

    var email by mutableStateOf(TextFieldModel(placeholder = "Почта"))
        private set

    var password by mutableStateOf(TextFieldModel(placeholder = "Пароль"))
        private set

    var loginButton by mutableStateOf(ButtonModel(text = "Войти"))
        private set

    fun onEmailChanged(value: String) {
        email = email.copy(value = value)
        email = email.copy(state = TextFieldState.Loading)
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

    fun onLoginButtonClick() {
        login()
    }

    fun login() {
        viewModelScope.launch {
            loginButton = loginButton.copy(state = ButtonState.Loading)
            val result = authRepository.login(email = email.value, password = password.value)

            result.onSuccess { success ->
                loginButton = loginButton.copy(state = ButtonState.Ready)
                Log.d("LOGIN", "SUCCESS")
                secureTokenStorage.saveTokens(accessToken = success.access.token, refreshToken = success.refresh.token)

                userDataStorage.updateUserData()
                globalAppState.setMainAppState()
            }.onFailure {
                Log.d("LOGIN", "FAILURE")
            }
        }
    }

    var refreshPasswordEmail by mutableStateOf(TextFieldModel(placeholder = "Почта"))
        private set

    var refreshPasswordCode by mutableStateOf(TextFieldModel(placeholder = "Код"))
        private set

    var newPassword by mutableStateOf(TextFieldModel(placeholder = "Пароль"))
        private set

    var isForwardButtonsEnabled by mutableStateOf(false)

    var sheetState by mutableStateOf<BottomSheetState>(BottomSheetState.isEmail)

    var refreshPasswordToken by mutableStateOf("")
        private set

    fun onRefreshPasswordEmailChanged(value: String) {
        refreshPasswordEmail = refreshPasswordEmail.copy(value = value)
        refreshPasswordEmail = refreshPasswordEmail.copy(state = validateEmail(refreshPasswordEmail.value))
        isForwardButtonsEnabled = refreshPasswordEmail.state !is TextFieldState.Error
    }

    fun onRefreshPasswordEmailButtonClick() {
        //TODO: SendRefreshPasswordCode
        viewModelScope.launch {
            val result = authRepository.sendVerificationResetPasswordCode(email = refreshPasswordEmail.value)
            result.onSuccess {
                Log.d("REFRESH_PASSWORD", "Code sent")
                sheetState = BottomSheetState.isCode
            }.onFailure {
                Log.d("REFRESH_PASSWORD", "Send code failure")
                refreshPasswordEmail = refreshPasswordEmail.copy(state = TextFieldState.Error("Такого пользователя не существует"))
            }
        }

    }

    fun onRefreshPasswordCodeChanged(value: String) {
        refreshPasswordCode = refreshPasswordCode.copy(value = value)
        if (refreshPasswordCode.value.length == 8) {
            //TODO: VerifyRefreshPasswordCode
            viewModelScope.launch {
                val result = authRepository.verifyResetPasswordCode(email = refreshPasswordEmail.value, code = refreshPasswordCode.value)
                Log.d("REFRESH_PASSWORD", "Code is ${result.getOrNull()?.response?.isSuccess}")
                Log.d("REFRESH_PASSWORD", "Message [${result.getOrNull()?.response?.message}]")
                result.onSuccess { success ->
                    refreshPasswordToken = success.resetPassword.token
                    Log.d("REFRESH_PASSWORD", "Code is correct")
                    sheetState = BottomSheetState.isPassword
                }.onFailure {
                    Log.d("REFRESH_PASSWORD", "Code uncorrected")
                    refreshPasswordCode = refreshPasswordCode.copy(value = "", state = TextFieldState.Error("Не верный код"))
                }
            }
        }
    }

    fun onNewPasswordChanged(value: String) {
        newPassword = newPassword.copy(value = value)
        if(newPassword.value.isBlank()) {
            newPassword = newPassword.copy(state = TextFieldState.Error("Empty"))
            isForwardButtonsEnabled = false
        } else {
            newPassword = newPassword.copy(state = TextFieldState.Default)
            isForwardButtonsEnabled = true
        }
        loginButton = loginButton.copy(state = validateFields())
    }

    fun onSaveNewPasswordButtonClick() {
        viewModelScope.launch {
            val result = authRepository.resetPassword(resetPasswordToken = refreshPasswordToken, password = newPassword.value)
            refreshPasswordToken = ""
            result.onSuccess {
                Log.d("REFRESH_PASSWORD", "New password saved")
                sheetState = BottomSheetState.isEmail
            }.onFailure {
                Log.d("REFRESH_PASSWORD", "Error")
            }
        }
    }

}