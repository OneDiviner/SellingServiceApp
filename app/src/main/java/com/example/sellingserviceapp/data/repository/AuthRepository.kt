package com.example.sellingserviceapp.data.repository

import com.example.sellingserviceapp.data.model.response.CreateVerificationEmailResponse
import com.example.sellingserviceapp.data.model.response.SendCodeToVerificationResponse
import com.example.sellingserviceapp.data.model.response.UserSecondStepRegisterResponse
import com.example.sellingserviceapp.data.model.response.UserStatusResponse
import com.example.sellingserviceapp.data.model.response.UsersFirstStepRegisterResponse

interface AuthRepository {

    suspend fun firstStepRegister(email: String, password: String): Result<UsersFirstStepRegisterResponse>

    suspend fun createVerificationEmail(email: String): Result<CreateVerificationEmailResponse>

    suspend fun sendCodeToVerification(email: String, code: String): Result<SendCodeToVerificationResponse>

    suspend fun userStatus(email: String): Result<UserStatusResponse>

    suspend fun secondStepRegister(
        token: String,
        firstName: String,
        secondName: String,
        lastName: String,
        phoneNumber: String
    ): Result<UserSecondStepRegisterResponse>

}

