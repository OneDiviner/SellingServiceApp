package com.example.sellingserviceapp.ui.screen.profile.clientProfile

import android.util.Base64
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.di.GlobalAppState
import com.example.sellingserviceapp.data.di.SecureTokenStorage
import com.example.sellingserviceapp.data.local.UserData
import com.example.sellingserviceapp.data.local.UserDataStorage
import com.example.sellingserviceapp.data.repository.AuthRepository
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonModel
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonState
import com.example.sellingserviceapp.ui.screen.profile.specialistProfile1.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

data class fullNameModel(
    val name: String,
    val secondName: String,
    val lastName: String
)

@HiltViewModel
class ClientProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userDataStorage: UserDataStorage,
    private val secureTokenStorage: SecureTokenStorage,
    private val globalAppState: GlobalAppState
): ViewModel() {

    val userFlow: Flow<UserData> = userDataStorage.getUser()

    var screenState by mutableStateOf<ScreenState>(ScreenState.Loading)
        private set

    var firstName by mutableStateOf("")
        private set
    var middleName by mutableStateOf("")
        private set
    var lastName by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var phoneNumber by mutableStateOf("")
        private set

    var saveChangesButton by mutableStateOf<ButtonModel>(ButtonModel("Сохранить изменения", state = ButtonState.Ready))
        private set

    private val _photoBase64 = mutableStateOf<String?>(null)
    val photoBase64: String? get() = _photoBase64.value

    init {
        viewModelScope.launch {
            screenState = ScreenState.Loading
            userDataStorage.getUser()
                .collect { userData ->
                    firstName = userData.firstName
                    middleName = userData.middleName
                    lastName = userData.lastName?: ""
                    email = userData.email
                    _photoBase64.value = userData.avatarBase64?: ""
                    //phoneNumber = userData.
                    screenState = ScreenState.Success
                }
        }
    }

    fun onFirstNameChange(value: String) {
        firstName = value
    }
    fun onMiddleNameChange(value: String) {
        middleName = value
    }
    fun onLastNameChange(value: String) {
        lastName = value
    }
    fun onEmailChange(value: String) {
        email = value
    }
    fun onPhoneNumberChange(value: String) {
        phoneNumber = value
    }

    fun onPhotoSelected(base64: String?) {
        _photoBase64.value = base64
        viewModelScope.launch {
            val decodedBytes = Base64.decode(base64, Base64.DEFAULT)
            val requestBody = decodedBytes.toRequestBody("image/jpg".toMediaTypeOrNull())
            val file = MultipartBody.Part.createFormData("multipartFile", "avatar.jpg", requestBody)
            val result = authRepository.updateAvatar(file)
            result.onSuccess {

            }
        }
    }

    fun onSaveChangesButtonClick() {
        viewModelScope.launch {
            val result = authRepository.updateUser(
                password = "9050",
                firstName = firstName,
                secondName = middleName,
                lastName = lastName,
                phoneNumber = phoneNumber
            )
            result.onSuccess { success ->
                userDataStorage.updateUserData()
            }.onFailure {

            }
        }
    }

    fun onExitButtonClick() {
        secureTokenStorage.clearTokens()
        globalAppState.setAuthState()
    }

}