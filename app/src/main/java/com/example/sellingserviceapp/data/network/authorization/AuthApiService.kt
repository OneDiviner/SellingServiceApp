package com.example.sellingserviceapp.data.network.authorization

import com.example.sellingserviceapp.data.network.authorization.request.CreateVerificationEmailRequest
import com.example.sellingserviceapp.data.network.authorization.request.LoginRequest
import com.example.sellingserviceapp.data.network.authorization.request.SendCodeToVerificationRequest
import com.example.sellingserviceapp.data.network.authorization.request.SecondStepRegisterRequest
import com.example.sellingserviceapp.data.network.authorization.request.FirstStepRegisterRequest
import com.example.sellingserviceapp.data.network.authorization.request.RefreshAccessTokenRequest
import com.example.sellingserviceapp.data.network.authorization.response.GetUserResponse
import com.example.sellingserviceapp.data.network.authorization.request.RefreshPasswordRequest
import com.example.sellingserviceapp.data.network.authorization.request.SendVerificationResetPasswordCodeRequest
import com.example.sellingserviceapp.data.network.authorization.request.UpdateUserRequest
import com.example.sellingserviceapp.data.network.authorization.request.VerifyResetPasswordCodeRequest
import com.example.sellingserviceapp.data.network.authorization.response.CreateVerificationEmailResponse
import com.example.sellingserviceapp.data.network.authorization.response.GetUserByIdResponse
import com.example.sellingserviceapp.data.network.authorization.response.LoginResponse
import com.example.sellingserviceapp.data.network.authorization.response.RefreshAccessTokenResponse
import com.example.sellingserviceapp.data.network.authorization.response.RefreshPasswordResponse
import com.example.sellingserviceapp.data.network.authorization.response.SendCodeToVerificationResponse
import com.example.sellingserviceapp.data.network.authorization.response.SendVerificationRefreshPasswordCodeResponse
import com.example.sellingserviceapp.data.network.authorization.response.UpdateAvatarResponse
import com.example.sellingserviceapp.data.network.authorization.response.UpdateUserResponse
import com.example.sellingserviceapp.data.network.authorization.response.UserSecondStepRegisterResponse
import com.example.sellingserviceapp.data.network.authorization.response.UserStatusResponse
import com.example.sellingserviceapp.data.network.authorization.response.UsersFirstStepRegisterResponse
import com.example.sellingserviceapp.data.network.authorization.response.VerifyResetPasswordCodeResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
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
    suspend fun updateUser(@Body request: UpdateUserRequest): Response<UpdateUserResponse>

    @Multipart
    @PATCH("/api/private/user/update-avatar")
    @Headers("Token: true")
    suspend fun updateAvatar(
        @Part file: MultipartBody.Part
    ): Response<UpdateAvatarResponse>

    @GET("/api/private/user/avatar")
    @Headers("Token: true")
    suspend fun getAvatar(@Query("avatarPath") avatarPath: String): Response<ResponseBody>

    @Headers("Token: true")
    @GET("/api/private/user/{ids}")
    suspend fun getUsersById(
        @Path("ids") userId: Int
    ): Response<GetUserByIdResponse>

    @Headers("Token: true")
    @GET("/api/private/user/{ids}")
    suspend fun getUsersListById(
        @Path("ids") userIds: String
    ): Response<GetUserByIdResponse>

}