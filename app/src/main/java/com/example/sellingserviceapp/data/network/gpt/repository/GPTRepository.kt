package com.example.sellingserviceapp.data.network.gpt.repository

import com.example.sellingserviceapp.data.network.AuthApiError
import com.example.sellingserviceapp.data.network.gpt.GPTApiService
import com.example.sellingserviceapp.data.network.gpt.request.GenerateImageForServiceRequest
import com.example.sellingserviceapp.data.network.gpt.response.GenerateImageForServiceResponse
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GPTRepository @Inject constructor(
    private val gptApiService: GPTApiService
) : IGPTRepository {
    override suspend fun generateImageForService(request: GenerateImageForServiceRequest): Result<GenerateImageForServiceResponse> {
        return try {
            val response = gptApiService.generateImageForService(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(AuthApiError.EmptyBody())
            } else {
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

    override suspend fun getGeneratedImage(fileId: String): Result<String> {
        return try {
            val response = gptApiService.getGeneratedImage(fileId)
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
}