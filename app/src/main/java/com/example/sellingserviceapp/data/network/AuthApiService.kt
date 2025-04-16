package com.example.sellingserviceapp.data.network

import com.example.sellingserviceapp.data.model.request.CreateVerificationEmailRequest
import com.example.sellingserviceapp.data.model.request.LoginRequest
import com.example.sellingserviceapp.data.model.request.SendCodeToVerificationRequest
import com.example.sellingserviceapp.data.model.request.SecondStepRegisterRequest
import com.example.sellingserviceapp.data.model.request.FirstStepRegisterRequest
import com.example.sellingserviceapp.data.model.request.RefreshPasswordRequest
import com.example.sellingserviceapp.data.model.request.SendVerificationResetPasswordCodeRequest
import com.example.sellingserviceapp.data.model.request.VerifyResetPasswordCodeRequest
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
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query



interface AuthApiService {

    @POST("/api/public/user/first-step-register")
    suspend fun firstStepRegister(@Body request: FirstStepRegisterRequest): Response<UsersFirstStepRegisterResponse>

    @POST("/api/public/code/send-verification-email")
    suspend fun createVerificationEmail(@Body request: CreateVerificationEmailRequest): Response<CreateVerificationEmailResponse>

    @POST("/api/public/code/verification-email")
    suspend fun sendCodeToVerification(@Body request: SendCodeToVerificationRequest): Response<SendCodeToVerificationResponse>

    @GET("/api/users/public/status")
    suspend fun userStatus(@Query("email") email: String): Response<UserStatusResponse>

    @PATCH("/api/users/public/second-step-register")
    suspend fun secondStepRegister(@Body request: SecondStepRegisterRequest): Response<UserSecondStepRegisterResponse>

    @POST("/api/users/public/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("/api/public/code/send-verification-reset-password")
    suspend fun sendVerificationResetPasswordCode(@Body request: SendVerificationResetPasswordCodeRequest): Response<SendVerificationRefreshPasswordCodeResponse>

    @POST("/api/public/code/verification-reset-password")
    suspend fun verifyResetPasswordCode(@Body request: VerifyResetPasswordCodeRequest): Response<VerifyResetPasswordCodeResponse>

    @POST("/api/public/user/reset-password")
    suspend fun resetPassword(@Body request: RefreshPasswordRequest): Response<RefreshPasswordResponse>

    @Multipart
    @POST("/api/users/private/update-avatar")
    suspend fun updateAvatar(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    ): Response<UpdateAvatarResponse>

    @GET("/api/users/private/profile")
    suspend fun getUserData(
        @Header("Authorization") token: String
    ):Response<GetUserData>

}