package com.example.sellingserviceapp.data.network

import com.example.sellingserviceapp.data.model.RetrofitInstance
import com.example.sellingserviceapp.data.model.request.UsersFirstStepRegisterRequest
import com.example.sellingserviceapp.data.model.response.UsersFirstStepRegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("api/users/public/first-step-register")
    suspend fun firstStepRegister( @Body request: UsersFirstStepRegisterRequest ): Response<UsersFirstStepRegisterResponse>

    companion object {
        fun create(): AuthApiService {
            return RetrofitInstance.retrofit.create(AuthApiService::class.java)
        }
    }
}