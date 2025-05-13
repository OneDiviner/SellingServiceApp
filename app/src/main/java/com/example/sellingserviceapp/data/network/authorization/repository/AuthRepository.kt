package com.example.sellingserviceapp.data.network.authorization.repository

import com.example.sellingserviceapp.data.network.authorization.response.CreateVerificationEmailResponse
import com.example.sellingserviceapp.data.network.authorization.response.LoginResponse
import com.example.sellingserviceapp.data.network.authorization.response.RefreshAccessTokenResponse
import com.example.sellingserviceapp.data.network.authorization.response.RefreshPasswordResponse
import com.example.sellingserviceapp.data.network.authorization.response.SendCodeToVerificationResponse
import com.example.sellingserviceapp.data.network.authorization.response.SendVerificationRefreshPasswordCodeResponse
import com.example.sellingserviceapp.data.network.authorization.response.UserSecondStepRegisterResponse
import com.example.sellingserviceapp.data.network.authorization.response.UserStatusResponse
import com.example.sellingserviceapp.data.network.authorization.response.UsersFirstStepRegisterResponse
import com.example.sellingserviceapp.data.network.authorization.response.VerifyResetPasswordCodeResponse
import com.example.sellingserviceapp.data.network.dto.UserDto
import okhttp3.MultipartBody



interface AuthRepository {

    suspend fun firstStepRegister(email: String, password: String): Result<UsersFirstStepRegisterResponse>

    suspend fun createVerificationEmail(email: String): Result<CreateVerificationEmailResponse>

    suspend fun sendCodeToVerification(email: String, code: String): Result<SendCodeToVerificationResponse>

    suspend fun userStatus(email: String): Result<UserStatusResponse>

    suspend fun sendVerificationResetPasswordCode(email: String): Result<SendVerificationRefreshPasswordCodeResponse>

    suspend fun verifyResetPasswordCode(email: String, code: String): Result<VerifyResetPasswordCodeResponse>

    suspend fun resetPassword(resetPasswordToken: String, password: String): Result<RefreshPasswordResponse>

    suspend fun getUser(): Result<UserDto>

    suspend fun refreshAccessToken(refreshToken: String): Result<RefreshAccessTokenResponse>

    suspend fun updateUser(userDto: UserDto): Result<UserDto>

    suspend fun secondStepRegister(
        token: String,
        firstName: String,
        secondName: String,
        lastName: String,
        phoneNumber: String
    ): Result<UserSecondStepRegisterResponse>

    suspend fun login(email: String, password: String): Result<LoginResponse>

    suspend fun updateAvatar(file: MultipartBody.Part): Result<UserDto>

    suspend fun getAvatar(avatarPath: String): Result<String>

}

