package com.example.sellingserviceapp.ui.screen.authentication.userinfo

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.w3c.dom.Text

class UserInfoViewModel: ViewModel() {
    var name by mutableStateOf("")
        private set

    var secondName by mutableStateOf("")
        private set

    var lastName by mutableStateOf("")
        private set

    var phoneNumber by mutableStateOf(TextFieldValue(""))
        private set

    var isFinishRegistrationButtonEnabled by mutableStateOf(false)
        private set

    /*private val _profileImageUri = MutableStateFlow<Uri?>(null)
    val profileImageUri: StateFlow<Uri?> = _profileImageUri

    fun setProfileImage(uri: Uri) {
        _profileImageUri.value = uri
    }*/

    fun onNameChanged(newName: String) {
        name = newName
    }

    fun onSecondNameChanged(newSecondName: String) {
        secondName = newSecondName
    }

    fun onLastNameChanged(newLastName: String) {
        lastName = newLastName
    }

    fun onPhoneNumberChanged(newPhoneNumber: TextFieldValue) {
        phoneNumber = newPhoneNumber
    }
}