package com.example.sellingserviceapp.data.repository

import com.example.sellingserviceapp.data.di.SecureTokenStorage
import com.example.sellingserviceapp.data.local.UserDataStore
import com.example.sellingserviceapp.data.local.UserName
import com.example.sellingserviceapp.data.model.AuthApiError
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
import com.example.sellingserviceapp.data.model.response.GetUserResponse
import com.example.sellingserviceapp.data.model.response.LoginResponse
import com.example.sellingserviceapp.data.model.response.RefreshPasswordResponse
import com.example.sellingserviceapp.data.model.response.SendCodeToVerificationResponse
import com.example.sellingserviceapp.data.model.response.SendVerificationRefreshPasswordCodeResponse
import com.example.sellingserviceapp.data.model.response.UpdateAvatarResponse
import com.example.sellingserviceapp.data.model.response.UserSecondStepRegisterResponse
import com.example.sellingserviceapp.data.model.response.UserStatusResponse
import com.example.sellingserviceapp.data.model.response.UsersFirstStepRegisterResponse
import com.example.sellingserviceapp.data.model.response.VerifyResetPasswordCodeResponse
import com.example.sellingserviceapp.data.network.AuthApiService
import okhttp3.MultipartBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject



class AuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService,
    private val secureTokenStorage: SecureTokenStorage,
): AuthRepository {

    override suspend fun firstStepRegister(
        email: String,
        password: String
    ): Result<UsersFirstStepRegisterResponse> {
        return try {
            val response = authApiService.firstStepRegister(FirstStepRegisterRequest(email, password))

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

    override suspend fun sendCodeToVerification(
        email: String,
        code: String
    ): Result<SendCodeToVerificationResponse> {
        return try {
            val response = authApiService.sendCodeToVerification(SendCodeToVerificationRequest(email, code))

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

    override suspend fun userStatus(
        email: String
    ):Result<UserStatusResponse> {
        return try {
            val response = authApiService.userStatus(email)

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

    override suspend fun secondStepRegister(
        token: String,
        firstName: String,
        secondName: String,
        lastName: String,
        phoneNumber: String
    ): Result<UserSecondStepRegisterResponse> {
        return try {
            val response = authApiService.secondStepRegister(
                SecondStepRegisterRequest(
                    token,
                    firstName,
                    secondName,
                    lastName,
                    phoneNumber
                )
            )

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

    override suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = authApiService.login(LoginRequest(email, password))

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

    override suspend fun getUserData(token: String): Result<GetUserData> {
        return try {
            val response = authApiService.getUserData(token)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(AuthApiError.HttpError(response.code(), "Upload failed: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            when (e) {
                is IOException -> Result.failure(AuthApiError.NetworkError("Ошибка подключения"))
                is HttpException -> Result.failure(AuthApiError.HttpError(e.code(), "Ошибка: ${e.message}"))
                else -> Result.failure(AuthApiError.UnknownError("Непредвиденная ошибка: ${e.message}"))
            }
        }
    }

    override suspend fun sendVerificationResetPasswordCode(email: String): Result<SendVerificationRefreshPasswordCodeResponse> {
        return try {
            val response = authApiService.sendVerificationResetPasswordCode(SendVerificationResetPasswordCodeRequest(email = email))
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(AuthApiError.HttpError(response.code(), "Upload failed: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            when (e) {
                is IOException -> Result.failure(AuthApiError.NetworkError("Ошибка подключения"))
                is HttpException -> Result.failure(AuthApiError.HttpError(e.code(), "Ошибка: ${e.message}"))
                else -> Result.failure(AuthApiError.UnknownError("Непредвиденная ошибка: ${e.message}"))
            }
        }
    }

    override suspend fun verifyResetPasswordCode(
        email: String,
        code: String
    ): Result<VerifyResetPasswordCodeResponse> {
        return try {
            val response = authApiService.verifyResetPasswordCode(VerifyResetPasswordCodeRequest(email, code))
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(AuthApiError.UnknownError("Пустой ответ от сервера"))
                }
            } else {
                Result.failure(AuthApiError.HttpError(response.code(), "Upload failed: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            when (e) {
                is IOException -> Result.failure(AuthApiError.NetworkError("Ошибка подключения"))
                is HttpException -> Result.failure(AuthApiError.HttpError(e.code(), "Ошибка: ${e.message}"))
                else -> Result.failure(AuthApiError.UnknownError("Непредвиденная ошибка: ${e.message}"))
            }
        }
    }

    override suspend fun resetPassword(
        resetPasswordToken: String,
        password: String
    ): Result<RefreshPasswordResponse> {
        return try {
            val response = authApiService.resetPassword(RefreshPasswordRequest(resetPasswordToken, password))
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(AuthApiError.HttpError(response.code(), "Upload failed: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            when (e) {
                is IOException -> Result.failure(AuthApiError.NetworkError("Ошибка подключения"))
                is HttpException -> Result.failure(AuthApiError.HttpError(e.code(), "Ошибка: ${e.message}"))
                else -> Result.failure(AuthApiError.UnknownError("Непредвиденная ошибка: ${e.message}"))
            }
        }
    }

    override suspend fun updateAvatar(file: MultipartBody.Part): Result<UpdateAvatarResponse> {
        return try {
            val token = secureTokenStorage.getAccessToken()?: return Result.failure(IllegalStateException("No access token"))
            val response = authApiService.updateAvatar(token = "Bearer $token", file)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(AuthApiError.HttpError(response.code(), "Upload failed: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            when (e) {
                is IOException -> Result.failure(AuthApiError.NetworkError("Ошибка подключения"))
                is HttpException -> Result.failure(AuthApiError.HttpError(e.code(), "Ошибка: ${e.message}"))
                else -> Result.failure(AuthApiError.UnknownError("Непредвиденная ошибка: ${e.message}"))
            }
        }
    }

    override suspend fun getUser(): Result<GetUserResponse> {
        return try {
            val token = secureTokenStorage.getAccessToken()
                ?: return Result.failure(IllegalStateException("No access token"))

            val response = authApiService.getUser(accessToken = "Bearer $token")

            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}