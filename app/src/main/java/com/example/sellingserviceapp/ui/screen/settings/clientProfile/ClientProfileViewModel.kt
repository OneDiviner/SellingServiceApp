package com.example.sellingserviceapp.ui.screen.settings.clientProfile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.ui.screen.settings.specialistProfile1.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

data class fullNameModel(
    val name: String,
    val secondName: String,
    val lastName: String
)

@HiltViewModel
class ClientProfileViewModel @Inject constructor(

): ViewModel() {
    var screenState by mutableStateOf<ScreenState>(ScreenState.Loading)
        private set

    var fullName by mutableStateOf("")
        private set

    var name by mutableStateOf("")
        private set
    var secondName by mutableStateOf("")
        private set
    var lastName by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var phoneNumber by mutableStateOf("")
        private set

    var location by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            screenState = ScreenState.Loading
            secondName = "Никитин"
            name = "Даниил"
            lastName = "Игоревич"
            fullName = secondName + name + lastName
            email = "dan.nikitin@mail.ru"
            delay(2000L)
            screenState = ScreenState.Success
        }
    }

    fun onNameChange(value: String) {
        name = value
    }
    fun onSecondNameChange(value: String) {
        secondName = value
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
}