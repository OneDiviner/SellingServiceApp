package com.example.sellingserviceapp.util.extension

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.UserAuthManager
import com.example.sellingserviceapp.data.repository.AuthRepository
import com.example.sellingserviceapp.ui.screen.authentication.userinfo.UserInfoViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

suspend fun UserInfoViewModel.secondStepRegisterRequest(authRepository: AuthRepository, userAuthManager: UserAuthManager) {

    coroutineScope {
        val job = launch {
            val token = userAuthManager.getToken()
            val result = token?.let {
                authRepository.secondStepRegister(
                    token = it,
                    firstName = name.value,
                    secondName = secondName.value,
                    lastName = lastName.value,
                    phoneNumber = phoneNumber.value
                )
            }

            if (result != null) {
                result.onSuccess { response ->
                    Log.d("CREATE_USER_SUCCESS", "SUCCESS: ${response.message}")
                }.onFailure { error ->
                    Log.d("CREATE_USER_ERROR", "ERROR: ${error.message}")
                }
            }
        }
        job.join()
    }
}