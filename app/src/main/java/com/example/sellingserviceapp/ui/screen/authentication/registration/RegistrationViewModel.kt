package com.example.sellingserviceapp.ui.screen.authentication.registration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.UserAuthManager
import com.example.sellingserviceapp.data.network.authorization.repository.AuthRepository
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonModel
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonState
import com.example.sellingserviceapp.ui.screen.authentication.state.TextFieldModel
import com.example.sellingserviceapp.ui.screen.authentication.state.TextFieldState
import com.example.sellingserviceapp.util.extension.firstStepRegisterRequest
import com.example.sellingserviceapp.util.extension.nextButtonValidateFields
import com.example.sellingserviceapp.util.extension.sendCodeToVerificationRequest
import com.example.sellingserviceapp.util.extension.updateTimer
import com.example.sellingserviceapp.util.extension.validateEmail
import com.example.sellingserviceapp.util.extension.validatePasswords
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userAuthManager: UserAuthManager
): ViewModel(), StateUpdater {

    private val _navigationEvents = MutableSharedFlow<String>(replay = 0)
    val navigationEvents: SharedFlow<String> = _navigationEvents

    var email by mutableStateOf(TextFieldModel(placeholder = "Почта"))
        private set

    var password by mutableStateOf(TextFieldModel(placeholder = "Пароль"))
        private set

    var confirmPassword by mutableStateOf(TextFieldModel(placeholder = "Введите пароль"))
        private set

    var emailConfirmCode by mutableStateOf(TextFieldModel(placeholder = "Введите код"))
        private set

    var nextButton by mutableStateOf(ButtonModel(text = "Продолжить"))
        private set

    var requestNewCodeButton by mutableStateOf(ButtonModel("Запросить"))
        private set

    var bottomSheetState by mutableStateOf(false)
        private set

    var timeLeft by mutableIntStateOf(60)
        private set

    var userStatus by mutableStateOf("")
        private set

    var token by mutableStateOf("")
        private set

    fun onEmailChanged(value: String) {
        email = email.copy(value = value)
        email = email.copy(state = validateEmail(email.value))
        nextButton = nextButton.copy(state = nextButtonValidateFields())
    }

    fun onPasswordChanged(value: String) {
        password = password.copy(value = value)
        confirmPassword = confirmPassword.copy(state = validatePasswords(password = password.value, confirmPassword = confirmPassword.value))
        nextButton = nextButton.copy(state = nextButtonValidateFields())
    }

    fun onConfirmPasswordChanged(value: String) {
        confirmPassword = confirmPassword.copy(value = value)
        confirmPassword = confirmPassword.copy(state = validatePasswords(password = password.value, confirmPassword = confirmPassword.value))
        nextButton = nextButton.copy(state = nextButtonValidateFields())
    }

    fun onNextButtonClick() {
        firstStepRegisterRequest(authRepository = authRepository, stateUpdater = this, userAuthManager = userAuthManager)
    }

    fun onEmailConfirmCodeChanged(value: String) {
        emailConfirmCode = emailConfirmCode.copy(value = value)
        //TODO: Сделать валидацию по таймеру
        if (emailConfirmCode.value.length == 8) {
            sendCodeToVerificationRequest(authRepository = authRepository, stateUpdater = this, userAuthManager)
        }
    }

    fun onRequestNewCodeButtonClick() {
        updateTimer(this)
        //TODO: Сделать запрос бд на создание нового кода
    }

    fun navigate() {
        viewModelScope.launch {
            _navigationEvents.emit("userInfo")
        }
    }

    override fun timeLeftUpdater(value: Int) {
        timeLeft = value
    }

    override fun tokenUpdater(value: String) {
        token = value
    }

    override fun userStatusUpdater(value: String) {
        userStatus = value
    }

    override fun nextButtonUpdater(nextButtonState: ButtonState) {
        nextButton = nextButton.copy(state = nextButtonState)
    }

    override fun emailConfirmCodeUpdater(emailConfirmCodeState: TextFieldState) {
        emailConfirmCode = emailConfirmCode.copy(state = emailConfirmCodeState)
    }

    override fun requestNewCodeButtonUpdater(requestNewCodeButtonState: ButtonState) {
        requestNewCodeButton = requestNewCodeButton.copy(state = requestNewCodeButtonState)
    }

    override fun bottomSheetStateUpdater(isOpen: Boolean) {
        bottomSheetState = isOpen
    }

}