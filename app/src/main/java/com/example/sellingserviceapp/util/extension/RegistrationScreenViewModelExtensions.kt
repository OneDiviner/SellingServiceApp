package com.example.sellingserviceapp.util.extension

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.UserAuthManager
import com.example.sellingserviceapp.data.repository.AuthRepository
import com.example.sellingserviceapp.ui.screen.authentication.registration.RegistrationViewModel
import com.example.sellingserviceapp.ui.screen.authentication.registration.StateUpdater
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonState
import com.example.sellingserviceapp.ui.screen.authentication.state.TextFieldState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun RegistrationViewModel.updateTimer(stateUpdater: StateUpdater) {
    stateUpdater.requestNewCodeButtonUpdater(ButtonState.Default)
    startTimer(60, stateUpdater)
}

fun RegistrationViewModel.startTimer(remainingTime: Int, stateUpdater: StateUpdater) {
    viewModelScope.launch {
        var timeLeft = remainingTime
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft -= 1
            stateUpdater.timeLeftUpdater(timeLeft)
        }
        stateUpdater.requestNewCodeButtonUpdater(ButtonState.Ready)
    }
}

fun RegistrationViewModel.userStatusRequest(authRepository: AuthRepository, stateUpdater: StateUpdater) {
    viewModelScope.launch {

        val result = authRepository.userStatus(email = email.value)

        result.onSuccess { response ->
            stateUpdater.userStatusUpdater(response.status)
            Log.d("FUN_GET_USER_STATUS", "GetUserStatus with email: $email, response.status = ${response.status}, userStatus = $userStatus")

        }.onFailure { error ->
            Log.d("FUN_GET_USER_STATUS", "GetUserStatus with email: $email, response.status = ${error.message}, userStatus = $userStatus")
        }
    }
}

fun RegistrationViewModel.createVerificationEmailCodeRequest(authRepository: AuthRepository, stateUpdater: StateUpdater) {
    viewModelScope.launch {

        val result = authRepository.createVerificationEmail(email.value)

        result.onSuccess { response ->
            Log.d("CREATE_CODE_SUCCESS", "SUCCESS: ${response.message}")

        }.onFailure { error ->
            Log.d("CREATE_CODE_FAILURE", "FAILURE: ${error.message}, Code already exist")
        }
    }
}

fun RegistrationViewModel.sendCodeToVerificationRequest(authRepository: AuthRepository, stateUpdater: StateUpdater, userAuthManager: UserAuthManager) {
    viewModelScope.launch {

        stateUpdater.emailConfirmCodeUpdater(TextFieldState.Loading)
        val result = authRepository.sendCodeToVerification(code = emailConfirmCode.value, email = email.value)

        result.onSuccess { response ->
            //TODO: Сохранить токен для перехода ко 2 этапу
            stateUpdater.tokenUpdater(response.emailToken)
            userAuthManager.saveAuthData(email = email.value, response.emailToken)
            Log.d("SAVE_TOKEN", "TOKEN_SAVED: ${userAuthManager.getToken()}, ${userAuthManager.getEmail()}")
            stateUpdater.emailConfirmCodeUpdater(TextFieldState.Default)
            stateUpdater.bottomSheetStateUpdater(false)
            navigate()
        }.onFailure {
            stateUpdater.emailConfirmCodeUpdater(TextFieldState.Error("Неправильный код"))
        }

    }
}

fun RegistrationViewModel.firstStepRegisterRequest(authRepository: AuthRepository, stateUpdater: StateUpdater, userAuthManager: UserAuthManager) {
    viewModelScope.launch {

        stateUpdater.nextButtonUpdater(ButtonState.Loading)
        val result = authRepository.firstStepRegister(email = email.value, password = password.value)

        result.onSuccess {
            stateUpdater.nextButtonUpdater(ButtonState.Ready)
            stateUpdater.bottomSheetStateUpdater(isOpen = true)
            startTimer(timeLeft, stateUpdater)
            createVerificationEmailCodeRequest(authRepository, stateUpdater)
            userAuthManager.clearAuthData()

        }.onFailure { error ->
            userStatusRequest(authRepository, stateUpdater)
            delay(1000L)
            Log.d("EXIST_TOKEN1", "TOKEN_IS: ${userAuthManager.isTokenValidForEmail(email = email.value)}")
            when {
                userStatus == "Not active" && !userAuthManager.isTokenValidForEmail(email = email.value) -> {
                    Log.d("EXIST_TOKEN2", "TOKEN_SAVED: ${userAuthManager.getToken()}, ${userAuthManager.getEmail()}")
                    stateUpdater.bottomSheetStateUpdater(isOpen = true)
                    startTimer(timeLeft, stateUpdater)
                    createVerificationEmailCodeRequest(authRepository, stateUpdater)
                    stateUpdater.nextButtonUpdater(ButtonState.Ready)
                    Log.d("FIRST_STEP_REGISTER_ERROR", "ERROR: ${error.message}, Status[$userStatus], NO_TOKEN")
                }
                userStatus == "Not active" && userAuthManager.isTokenValidForEmail(email = email.value) -> {
                    stateUpdater.nextButtonUpdater(ButtonState.Ready)
                    navigate()
                    Log.d("FIRST_STEP_REGISTER_ERROR", "ERROR: ${error.message}, Status[$userStatus], TOKEN_EXIST")
                }
                userStatus == "Active" -> {
                    stateUpdater.nextButtonUpdater(ButtonState.Error("Такая почта уже существует"))
                    Log.d("FIRST_STEP_REGISTER_ERROR", "ERROR: ${error.message}, Email already exist try new")
                }
            }
        }
    }
}