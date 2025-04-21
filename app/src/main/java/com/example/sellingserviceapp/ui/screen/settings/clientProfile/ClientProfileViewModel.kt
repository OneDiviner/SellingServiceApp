package com.example.sellingserviceapp.ui.screen.settings.clientProfile

import android.util.Base64
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.repository.AuthRepository
import com.example.sellingserviceapp.ui.screen.settings.specialistProfile1.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
    private val authRepository: AuthRepository
): ViewModel() {
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

    var location by mutableStateOf("")
        private set

    private val _photoBase64 = mutableStateOf<String?>(null)
    val photoBase64: String? get() = _photoBase64.value

    init {
        screenState = ScreenState.Loading
        viewModelScope.launch {
            val result = authRepository.getUser()
            result.onSuccess { success ->
                firstName = success.user.firstName
                middleName = success.user.middleName
                lastName = success.user.lastName
                email = success.user.email
                phoneNumber = success.user.phoneNumber
                _photoBase64.value = success.user.avatar
                /*userDataStore.saveUser(
                    user = UserName(
                        firstName = success.user.firstName,
                        middleName = success.user.middleName,
                        lastName = success.user.lastName
                    )
                )*/ //TODO: См. TODO выше
                delay(2000L)
                screenState = ScreenState.Success
            }.onFailure {

            }
        }
    }

    fun onNameChange(value: String) {
        firstName = value
    }
    fun onSecondNameChange(value: String) {
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
    fun onLocationChange(value: String) {
        location = value
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

}