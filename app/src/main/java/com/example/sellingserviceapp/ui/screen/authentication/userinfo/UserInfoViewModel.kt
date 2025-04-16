package com.example.sellingserviceapp.ui.screen.authentication.userinfo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.sellingserviceapp.UserAuthManager
import com.example.sellingserviceapp.data.repository.AuthRepository
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonModel
import com.example.sellingserviceapp.ui.screen.authentication.state.TextFieldModel
import com.example.sellingserviceapp.util.extension.secondStepRegisterRequest
import com.example.sellingserviceapp.util.extension.validateFields
import com.example.sellingserviceapp.util.extension.validateUserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//TODO: Сделать валидацию полей используя Extensions //COMPLETE
//TODO: Сделать валидацию самих полей //COMPLETE
//TODO: Сделать ошибки полей красивыми //IN_PROGRESS
//TODO: Сделать состояние кнопки как у TextField //IN_PROGRESS

@HiltViewModel
class UserInfoViewModel@Inject constructor(
    private val authRepository: AuthRepository,
    private val userAuthManager: UserAuthManager
): ViewModel() {

    var secondName by mutableStateOf(TextFieldModel(placeholder = "Фамилия"))
        private set

    var name by mutableStateOf(TextFieldModel(placeholder = "Имя"))
        private set

    var lastName by mutableStateOf(TextFieldModel(placeholder = "Отчество"))
        private set

    var phoneNumber by mutableStateOf(TextFieldModel(placeholder = "Номер телефона"))
        private set

    var finishRegisterButton by mutableStateOf(ButtonModel("Завершить"))
        private set


    /*private val _profileImageUri = MutableStateFlow<Uri?>(null)
    val profileImageUri: StateFlow<Uri?> = _profileImageUri

    fun setProfileImage(uri: Uri) {
        _profileImageUri.value = uri
    }*/

    fun onSecondNameChanged(value: String) {
        secondName = secondName.copy(value = value)
        secondName = secondName.copy(state = validateUserInfo(secondName.value))
        finishRegisterButton = finishRegisterButton.copy(state = validateFields())
    }

    fun onNameChanged(value: String) {
        name = name.copy(value = value)
        name = name.copy(state = validateUserInfo(name.value))
        finishRegisterButton = finishRegisterButton.copy(state = validateFields())
    }

    fun onLastNameChanged(value: String) {
        lastName = lastName.copy(value = value)
        lastName = lastName.copy(state = validateUserInfo(lastName.value))
        finishRegisterButton = finishRegisterButton.copy(state = validateFields())
    }

    fun onPhoneNumberChanged(value: String) {
        phoneNumber = phoneNumber.copy(value = value)
        //phoneNumber = phoneNumber.copy() //TODO: Сделать валидатор либо преобразовывать ввод
        finishRegisterButton = finishRegisterButton.copy(state = validateFields())
    }

    fun onFinishRegisterButtonClick() {
        secondStepRegisterRequest(authRepository = authRepository, userAuthManager)
    }
}