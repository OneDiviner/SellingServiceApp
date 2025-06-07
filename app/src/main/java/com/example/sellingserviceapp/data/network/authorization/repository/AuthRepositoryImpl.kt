package com.example.sellingserviceapp.data.network.authorization.repository

import android.util.Log
import com.example.sellingserviceapp.data.di.SecureTokenStorage
import com.example.sellingserviceapp.data.network.AuthApiError
import com.example.sellingserviceapp.data.network.authorization.request.CreateVerificationEmailRequest
import com.example.sellingserviceapp.data.network.authorization.request.LoginRequest
import com.example.sellingserviceapp.data.network.authorization.request.SendCodeToVerificationRequest
import com.example.sellingserviceapp.data.network.authorization.request.SecondStepRegisterRequest
import com.example.sellingserviceapp.data.network.authorization.request.FirstStepRegisterRequest
import com.example.sellingserviceapp.data.network.authorization.request.RefreshAccessTokenRequest
import com.example.sellingserviceapp.data.network.authorization.request.RefreshPasswordRequest
import com.example.sellingserviceapp.data.network.authorization.request.SendVerificationResetPasswordCodeRequest
import com.example.sellingserviceapp.data.network.authorization.request.UpdateUserRequest
import com.example.sellingserviceapp.data.network.authorization.request.VerifyResetPasswordCodeRequest
import com.example.sellingserviceapp.data.network.authorization.response.CreateVerificationEmailResponse
import com.example.sellingserviceapp.data.network.authorization.response.LoginResponse
import com.example.sellingserviceapp.data.network.authorization.response.RefreshAccessTokenResponse
import com.example.sellingserviceapp.data.network.authorization.response.RefreshPasswordResponse
import com.example.sellingserviceapp.data.network.authorization.response.SendCodeToVerificationResponse
import com.example.sellingserviceapp.data.network.authorization.response.SendVerificationRefreshPasswordCodeResponse
import com.example.sellingserviceapp.data.network.authorization.response.UserSecondStepRegisterResponse
import com.example.sellingserviceapp.data.network.authorization.response.UserStatusResponse
import com.example.sellingserviceapp.data.network.authorization.response.UsersFirstStepRegisterResponse
import com.example.sellingserviceapp.data.network.authorization.response.VerifyResetPasswordCodeResponse
import com.example.sellingserviceapp.data.network.authorization.AuthApiService
import com.example.sellingserviceapp.model.dto.UserDto
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
            val response = authApiService.sendVerificationResetPasswordCode(
                SendVerificationResetPasswordCodeRequest(email = email)
            )
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

    override suspend fun updateAvatar(file: MultipartBody.Part): Result<UserDto> {
        return try {
            val response = authApiService.updateAvatar(file)
            if (response.isSuccessful) {
                Result.success(response.body()!!.user)
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

    override suspend fun getAvatar(avatarPath: String): Result<String> {
        return try {
            val response = authApiService.getAvatar(avatarPath = avatarPath)
            if (response.isSuccessful) {
                Result.success(
                    response.body()?.byteStream()?.use { stream ->
                        val bytes = stream.readBytes()
                        android.util.Base64.encodeToString(bytes, android.util.Base64.DEFAULT)
                    } ?: throw IOException("Empty avatar response")
                )
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUser(): Result<UserDto> {
        return try {
            val response = authApiService.getUser()

            if (response.isSuccessful) {
                Result.success(response.body()!!.user)
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateUser(userDto: UserDto): Result<UserDto> {
        return try {
            val response = authApiService.updateUser(
                request = UpdateUserRequest(
                    password = null,
                    firstName = userDto.firstName,
                    middleName = userDto.secondName,
                    lastName = userDto.lastName,
                    phoneNumber = userDto.phoneNumber
                )
            )
            if (response.isSuccessful) {
                Result.success(response.body()!!.userDto)
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

    override suspend fun getUserById(userId: Int): Result<UserDto> {
        return try {
            val response = authApiService.getUsersById(userId)
            if (response.isSuccessful) {
                Result.success(response.body()!!.listUsersDto.first())
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

    override suspend fun getUsersListById(userIds: List<Int>): Result<List<UserDto>> {
        return try {
            val response = authApiService.getUsersListById(userIds.joinToString(","))
            if (response.isSuccessful) {
                Result.success(response.body()!!.listUsersDto)
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