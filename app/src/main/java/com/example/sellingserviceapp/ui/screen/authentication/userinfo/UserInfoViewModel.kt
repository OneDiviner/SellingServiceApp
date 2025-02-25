package com.example.sellingserviceapp.ui.screen.authentication.userinfo

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.example.sellingserviceapp.ui.screen.authentication.state.TextFieldState

class UserInfoViewModel: ViewModel() {
    private var _nameState = mutableStateOf(TextFieldState())
    val nameState: State<TextFieldState> = _nameState

    private var _secondNameState = mutableStateOf(TextFieldState())
    val secondNameState: State<TextFieldState> = _secondNameState

    private var _lastNameState = mutableStateOf(TextFieldState())
    val lastNameState: State<TextFieldState> = _lastNameState

    private var _phoneNumberState = mutableStateOf(TextFieldState())
    val phoneNumberState: State<TextFieldState> = _phoneNumberState

    var isFinishRegistrationButtonEnabled by mutableStateOf(false)
        private set

    /*private val _profileImageUri = MutableStateFlow<Uri?>(null)
    val profileImageUri: StateFlow<Uri?> = _profileImageUri

    fun setProfileImage(uri: Uri) {
        _profileImageUri.value = uri
    }*/

    fun onNameChanged(name: String) {
        _nameState.value = nameState.value.copy(
            text = name,
            error = ""
        )
    }

    fun onSecondNameChanged(secondName: String) {
        _secondNameState.value = secondNameState.value.copy(
            text = secondName,
            error = ""
        )
    }

    fun onLastNameChanged(lastName: String) {
        _lastNameState.value = lastNameState.value.copy(
            text = lastName,
            error = ""
        )
    }

    fun onPhoneNumberChanged(phoneNumber: String) {
        _phoneNumberState.value = phoneNumberState.value.copy(
            text = phoneNumber,
            error = ""
        )
    }
}