package com.example.sellingserviceapp.data.repository

import com.example.sellingserviceapp.data.di.SecureTokenStorage
import com.example.sellingserviceapp.data.model.AuthApiError
import com.example.sellingserviceapp.data.model.authApiRequest.CreateVerificationEmailRequest
import com.example.sellingserviceapp.data.model.authApiRequest.LoginRequest
import com.example.sellingserviceapp.data.model.authApiRequest.SendCodeToVerificationRequest
import com.example.sellingserviceapp.data.model.authApiRequest.SecondStepRegisterRequest
import com.example.sellingserviceapp.data.model.authApiRequest.FirstStepRegisterRequest
import com.example.sellingserviceapp.data.model.authApiRequest.RefreshAccessTokenRequest
import com.example.sellingserviceapp.data.model.authApiRequest.RefreshPasswordRequest
import com.example.sellingserviceapp.data.model.authApiRequest.SendVerificationResetPasswordCodeRequest
import com.example.sellingserviceapp.data.model.authApiRequest.UpdateUserRequest
import com.example.sellingserviceapp.data.model.authApiRequest.VerifyResetPasswordCodeRequest
import com.example.sellingserviceapp.data.model.AuthApiResponse.CreateVerificationEmailResponse
import com.example.sellingserviceapp.data.model.AuthApiResponse.GetUserResponse
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
            val response = authApiService.updateAvatar(file)
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

            val response = authApiService.getUser()

            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateUser(
        password: String,
        firstName: String,
        secondName: String,
        lastName: String,
        phoneNumber: String
    ): Result<UpdateUserResponse> {
        return try {

            val response = authApiService.updateUser(request = UpdateUserRequest(
                password = password,
                firstName = firstName,
                middleName = secondName,
                lastName = lastName,
                phoneNumber = phoneNumber
            ))
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

    override suspend fun refreshAccessToken(refreshToken: String): Result<RefreshAccessTokenResponse> {
        return try {

            val response = authApiService.refreshAccessToken(RefreshAccessTokenRequest(refreshToken))
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

}