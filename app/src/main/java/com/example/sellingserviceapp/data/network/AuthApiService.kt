package com.example.sellingserviceapp.data.network

import android.util.Log
import com.example.sellingserviceapp.data.model.request.CreateVerificationEmailRequest
import com.example.sellingserviceapp.data.model.request.SendCodeToVerificationRequest
import com.example.sellingserviceapp.data.model.request.UserSecondStepRegisterRequest
import com.example.sellingserviceapp.data.model.request.UserStatusRequest
import com.example.sellingserviceapp.data.model.request.UsersFirstStepRegisterRequest
import com.example.sellingserviceapp.data.model.response.CreateVerificationEmailResponse
import com.example.sellingserviceapp.data.model.response.SendCodeToVerificationResponse
import com.example.sellingserviceapp.data.model.response.UserSecondStepRegisterResponse
import com.example.sellingserviceapp.data.model.response.UserStatusResponse
import com.example.sellingserviceapp.data.model.response.UsersFirstStepRegisterResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApiService {

    @POST("api/users/public/first-step-register")
    suspend fun firstStepRegister(@Body request: UsersFirstStepRegisterRequest): Response<UsersFirstStepRegisterResponse>

    @POST("/api/codes/public/create-verification-email")
    suspend fun createVerificationEmail(@Body request: CreateVerificationEmailRequest): Response<CreateVerificationEmailResponse>

    @PATCH("/api/codes/public/verification-email")
    suspend fun sendCodeToVerification(@Body request: SendCodeToVerificationRequest): Response<SendCodeToVerificationResponse>

    @GET("/api/users/public/status")
    suspend fun userStatus(@Query("email") email: String): Response<UserStatusResponse>

    @PATCH("/api/users/public/second-step-register")
    suspend fun secondStepRegister(@Body request: UserSecondStepRegisterRequest): Response<UserSecondStepRegisterResponse>

}