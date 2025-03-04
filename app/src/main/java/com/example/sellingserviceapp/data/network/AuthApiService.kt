package com.example.sellingserviceapp.data.network

import android.util.Log
import com.example.sellingserviceapp.data.model.request.CreateVerificationEmailRequest
import com.example.sellingserviceapp.data.model.request.UsersFirstStepRegisterRequest
import com.example.sellingserviceapp.data.model.response.CreateVerificationEmailResponse
import com.example.sellingserviceapp.data.model.response.UsersFirstStepRegisterResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("api/users/public/first-step-register")
    suspend fun firstStepRegister(@Body request: UsersFirstStepRegisterRequest): Response<UsersFirstStepRegisterResponse>

    @POST("/api/codes/public/create-verification-email")
    suspend fun createVerificationEmail(@Body request: CreateVerificationEmailRequest): Response<CreateVerificationEmailResponse>

}