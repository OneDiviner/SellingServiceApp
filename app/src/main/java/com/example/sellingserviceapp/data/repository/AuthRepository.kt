package com.example.sellingserviceapp.data.repository

import com.example.sellingserviceapp.data.model.AuthApiResponse.GetUserResponse
import com.example.sellingserviceapp.data.model.AuthApiResponse.CreateVerificationEmailResponse
import com.example.sellingserviceapp.data.model.AuthApiResponse.LoginResponse
import com.example.sellingserviceapp.data.model.AuthApiResponse.RefreshAccessTokenResponse
import com.example.sellingserviceapp.data.model.AuthApiResponse.RefreshPasswordResponse
import com.example.sellingserviceapp.data.model.AuthApiResponse.SendCodeToVerificationResponse
import com.example.sellingserviceapp.data.model.AuthApiResponse.SendVerificationRefreshPasswordCodeResponse
import com.example.sellingserviceapp.data.model.AuthApiResponse.UpdateAvatarResponse
import com.example.sellingserviceapp.data.model.AuthApiResponse.UpdateUserResponse
import com.example.sellingserviceapp.data.model.AuthApiResponse.UserSecondStepRegisterResponse
import com.example.sellingserviceapp.data.model.AuthApiResponse.UserStatusResponse
import com.example.sellingserviceapp.data.model.AuthApiResponse.UsersFirstStepRegisterResponse
import com.example.sellingserviceapp.data.model.AuthApiResponse.VerifyResetPasswordCodeResponse
import okhttp3.MultipartBody

interface AuthRepository {

    suspend fun firstStepRegister(email: String, password: String): Result<UsersFirstStepRegisterResponse>

    suspend fun createVerificationEmail(email: String): Result<CreateVerificationEmailResponse>

    suspend fun sendCodeToVerification(email: String, code: String): Result<SendCodeToVerificationResponse>

    suspend fun userStatus(email: String): Result<UserStatusResponse>

    suspend fun sendVerificationResetPasswordCode(email: String): Result<SendVerificationRefreshPasswordCodeResponse>

    suspend fun verifyResetPasswordCode(email: String, code: String): Result<VerifyResetPasswordCodeResponse>

    suspend fun resetPassword(resetPasswordToken: String, password: String): Result<RefreshPasswordResponse>

    suspend fun getUser(): Result<GetUserResponse>

    suspend fun refreshAccessToken(refreshToken: String): Result<RefreshAccessTokenResponse>

    suspend fun updateUser(
        password: String,
        firstName: String,
        secondName: String,
        lastName: String,
        phoneNumber: String
    ): Result<UpdateUserResponse>

    suspend fun secondStepRegister(
        token: String,
        firstName: String,
        secondName: String,
        lastName: String,
        phoneNumber: String
    ): Result<UserSecondStepRegisterResponse>

    suspend fun login(email: String, password: String): Result<LoginResponse>

    suspend fun updateAvatar(file: MultipartBody.Part): Result<UpdateAvatarResponse>

}

