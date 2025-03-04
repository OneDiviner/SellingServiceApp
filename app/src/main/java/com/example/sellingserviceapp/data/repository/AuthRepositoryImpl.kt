package com.example.sellingserviceapp.data.repository

import com.example.sellingserviceapp.data.model.AuthApiError
import com.example.sellingserviceapp.data.model.request.CreateVerificationEmailRequest
import com.example.sellingserviceapp.data.model.request.UsersFirstStepRegisterRequest
import com.example.sellingserviceapp.data.model.response.CreateVerificationEmailResponse
import com.example.sellingserviceapp.data.model.response.UsersFirstStepRegisterResponse
import com.example.sellingserviceapp.data.network.AuthApiService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService
): AuthRepository {

    override suspend fun firstStepRegister(
        email: String,
        password: String
    ): Result<UsersFirstStepRegisterResponse> {
        return try {
            val response = authApiService.firstStepRegister(UsersFirstStepRegisterRequest(email, password))

            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(AuthApiError.EmptyBody())
            } else {
                //Добавить обработчик кодов ошибки
                Result.failure(AuthApiError.HttpError(response.code(), "Ошибка: ${response.code()}"))
            }
        } catch (e: Exception) {
            when (e) {
                is IOException -> Result.failure(AuthApiError.NetworkError("Нет интернет соединения"))
                is HttpException -> Result.failure(AuthApiError.HttpError(e.code(), "Ошибка: ${e.message}"))
                else -> Result.failure(AuthApiError.UnknownError("Непредвиденная ошибка: ${e.message}"))
            }
        }
    }

    override suspend fun createVerificationEmail(
        email: String
    ): Result<CreateVerificationEmailResponse> {
        return try {
            val response = authApiService.createVerificationEmail(CreateVerificationEmailRequest(email))

            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(AuthApiError.EmptyBody())
            } else {
                //Добавить обработчик кодов ошибки
                Result.failure(AuthApiError.HttpError(response.code(), "Ошибка: ${response.code()}"))
            }
        } catch (e: Exception) {
            when (e) {
                is IOException -> Result.failure(AuthApiError.NetworkError("Нет интернет соединения"))
                is HttpException -> Result.failure(AuthApiError.HttpError(e.code(), "Ошибка: ${e.message}"))
                else -> Result.failure(AuthApiError.UnknownError("Непредвиденная ошибка: ${e.message}"))
            }
        }
    }

}