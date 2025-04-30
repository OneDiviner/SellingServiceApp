package com.example.sellingserviceapp.data.network

import com.example.sellingserviceapp.data.model.authApiRequest.CreateVerificationEmailRequest
import com.example.sellingserviceapp.data.model.authApiRequest.LoginRequest
import com.example.sellingserviceapp.data.model.authApiRequest.SendCodeToVerificationRequest
import com.example.sellingserviceapp.data.model.authApiRequest.SecondStepRegisterRequest
import com.example.sellingserviceapp.data.model.authApiRequest.FirstStepRegisterRequest
import com.example.sellingserviceapp.data.model.authApiRequest.RefreshAccessTokenRequest
import com.example.sellingserviceapp.data.model.AuthApiResponse.GetUserResponse
import com.example.sellingserviceapp.data.model.authApiRequest.RefreshPasswordRequest
import com.example.sellingserviceapp.data.model.authApiRequest.SendVerificationResetPasswordCodeRequest
import com.example.sellingserviceapp.data.model.authApiRequest.UpdateUserRequest
import com.example.sellingserviceapp.data.model.authApiRequest.VerifyResetPasswordCodeRequest
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
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
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

    @POST("/api/public/user/second-step-register")
    suspend fun secondStepRegister(@Body request: SecondStepRegisterRequest): Response<UserSecondStepRegisterResponse>

    @POST("/api/public/user/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("/api/public/code/send-verification-reset-password")
    suspend fun sendVerificationResetPasswordCode(@Body request: SendVerificationResetPasswordCodeRequest): Response<SendVerificationRefreshPasswordCodeResponse>

    @POST("/api/public/code/verification-reset-password")
    suspend fun verifyResetPasswordCode(@Body request: VerifyResetPasswordCodeRequest): Response<VerifyResetPasswordCodeResponse>

    @POST("/api/public/user/reset-password")
    suspend fun resetPassword(@Body request: RefreshPasswordRequest): Response<RefreshPasswordResponse>

    @GET("/api/private/user/profile")
    @Headers("Token: true")
    suspend fun getUser(): Response<GetUserResponse>

    @POST("/api/public/user/refresh-access-token")
    suspend fun refreshAccessToken(@Body request: RefreshAccessTokenRequest): Response<RefreshAccessTokenResponse>

    @PATCH("/api/private/user/update")
    @Headers("Token: true")
    suspend fun updateUser(
        @Body request: UpdateUserRequest
    ): Response<UpdateUserResponse>

    @Multipart
    @PATCH("/api/private/user/update-avatar")
    @Headers("Token: true")
    suspend fun updateAvatar(
        @Part file: MultipartBody.Part
    ): Response<UpdateAvatarResponse>

}