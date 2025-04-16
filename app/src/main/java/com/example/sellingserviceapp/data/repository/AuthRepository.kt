package com.example.sellingserviceapp.data.repository

import com.example.sellingserviceapp.data.model.response.CreateVerificationEmailResponse
import com.example.sellingserviceapp.data.model.response.GetUserData
import com.example.sellingserviceapp.data.model.response.LoginResponse
import com.example.sellingserviceapp.data.model.response.RefreshPasswordResponse
import com.example.sellingserviceapp.data.model.response.SendCodeToVerificationResponse
import com.example.sellingserviceapp.data.model.response.SendVerificationRefreshPasswordCodeResponse
import com.example.sellingserviceapp.data.model.response.UpdateAvatarResponse
import com.example.sellingserviceapp.data.model.response.UserSecondStepRegisterResponse
import com.example.sellingserviceapp.data.model.response.UserStatusResponse
import com.example.sellingserviceapp.data.model.response.UsersFirstStepRegisterResponse
import com.example.sellingserviceapp.data.model.response.VerifyResetPasswordCodeResponse
import okhttp3.MultipartBody

interface AuthRepository {

    suspend fun firstStepRegister(email: String, password: String): Result<UsersFirstStepRegisterResponse>

    suspend fun createVerificationEmail(email: String): Result<CreateVerificationEmailResponse>

    suspend fun sendCodeToVerification(email: String, code: String): Result<SendCodeToVerificationResponse>

    suspend fun userStatus(email: String): Result<UserStatusResponse>

    suspend fun sendVerificationResetPasswordCode(email: String): Result<SendVerificationRefreshPasswordCodeResponse>

    suspend fun verifyResetPasswordCode(email: String, code: String): Result<VerifyResetPasswordCodeResponse>

    suspend fun resetPassword(resetPasswordToken: String, password: String): Result<RefreshPasswordResponse>

    suspend fun secondStepRegister(
        token: String,
        firstName: String,
        secondName: String,
        lastName: String,
        phoneNumber: String
    ): Result<UserSecondStepRegisterResponse>

    suspend fun login(email: String, password: String): Result<LoginResponse>

    suspend fun updateAvatar(token: String, file: MultipartBody.Part): Result<UpdateAvatarResponse>

    suspend fun getUserData(token: String):Result<GetUserData>

}

