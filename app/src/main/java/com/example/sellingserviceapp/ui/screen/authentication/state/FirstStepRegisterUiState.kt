package com.example.sellingserviceapp.ui.screen.authentication.state

import com.example.sellingserviceapp.data.network.authorization.response.UsersFirstStepRegisterResponse

sealed class FirstStepRegisterUiState {
    data class Success(val userFirstStepResponse: Result<UsersFirstStepRegisterResponse>) : FirstStepRegisterUiState()
    data class Error(val message: String) : FirstStepRegisterUiState()
    object Loading : FirstStepRegisterUiState()
}