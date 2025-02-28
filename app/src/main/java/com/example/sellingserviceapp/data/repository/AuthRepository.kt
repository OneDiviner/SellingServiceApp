package com.example.sellingserviceapp.data.repository

import com.example.sellingserviceapp.data.model.request.UsersFirstStepRegisterRequest
import com.example.sellingserviceapp.data.model.response.UsersFirstStepRegisterResponse
import com.example.sellingserviceapp.data.network.AuthApiService

class AuthRepository(private val apiService: AuthApiService) {
    suspend fun registerFirstStep(email: String, password: String): Result<UsersFirstStepRegisterResponse> {
        return try {
            val request = UsersFirstStepRegisterRequest(email, password)
            val response = apiService.firstStepRegister(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Response body is null"))
            } else {
                Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}