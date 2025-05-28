package com.example.sellingserviceapp.ui.screen.profile.editProfile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.DataManager
import com.example.sellingserviceapp.model.domain.UserDomain
import com.example.sellingserviceapp.ui.screen.profile.editProfile.EditProfileValidators.isChanged
import com.example.sellingserviceapp.ui.screen.profile.editProfile.EditProfileValidators.validateFirstName
import com.example.sellingserviceapp.ui.screen.profile.editProfile.EditProfileValidators.validateLastName
import com.example.sellingserviceapp.ui.screen.profile.editProfile.EditProfileValidators.validatePhoneNumber
import com.example.sellingserviceapp.ui.screen.profile.editProfile.EditProfileValidators.validateSecondName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Error

sealed class EditUserError {
    data class FirstNameError(var message: String? = null): EditUserError()
    data class SecondNameError(var message: String? = null): EditUserError()
    data class LastNameError(var message: String? = null): EditUserError()
    data class PhoneNumberError(var message: String? = null): EditUserError()
}

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val dataManager: DataManager
): ViewModel() {

    var newUserData by mutableStateOf<UserDomain>(UserDomain.EMPTY)
    var originalUser by mutableStateOf<UserDomain>(UserDomain.EMPTY)
    var editUserError by mutableStateOf<EditUserError?>(EditUserError.FirstNameError())

    init {
        viewModelScope.launch {
            dataManager.getUserFlow().first().let { user->
                newUserData = user.copy()
                originalUser = user.copy()
            }
        }
    }

    fun onFirstNameChanged(value: String) {
        newUserData = newUserData.copy(firstName = value)
        val error: String? = newUserData.validateFirstName()
        editUserError = if (error == null) {
            error
        } else {
            EditUserError.FirstNameError(error)
        }
    }
    fun onSecondNameChanged(value: String) {
        newUserData = newUserData.copy(secondName = value)
        val error: String? = newUserData.validateSecondName()
        editUserError = if (error == null) {
            error
        } else {
            EditUserError.SecondNameError(error)
        }
    }
    fun onLastNameChanged(value: String) {
        newUserData = newUserData.copy(lastName = value)
        val error: String? = newUserData.validateLastName()
        editUserError = if (error == null) {
            error
        } else {
            EditUserError.LastNameError(error)
        }
    }
    fun onPhoneNumberChanged(value: String) {
        newUserData = newUserData.copy(phoneNumber = value)
        val error: String? = newUserData.validatePhoneNumber()
        editUserError = if (error == null) {
            error
        } else {
            EditUserError.PhoneNumberError(error)
        }
    }
    fun updateUser() {
        viewModelScope.launch {
            dataManager.updateUser(newUserData)
            dataManager.getUserFlow().first().let { user ->
                //Log.d("CHANGED_USER", newUserData.getChangedFields(user).toString())
                dataManager.updateUser(newUserData.isChanged(originalUser))
            }
        }
    }

}