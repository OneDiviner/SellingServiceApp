package com.example.sellingserviceapp.data.repository

import com.example.sellingserviceapp.data.model.response.CreateVerificationEmailResponse
import com.example.sellingserviceapp.data.model.response.UsersFirstStepRegisterResponse

interface AuthRepository {

    suspend fun firstStepRegister(email: String, password: String): Result<UsersFirstStepRegisterResponse>

    suspend fun createVerificationEmail(email: String): Result<CreateVerificationEmailResponse>

}

