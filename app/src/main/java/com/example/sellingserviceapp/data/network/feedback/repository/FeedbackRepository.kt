package com.example.sellingserviceapp.data.network.feedback.repository

import com.example.sellingserviceapp.data.network.AuthApiError
import com.example.sellingserviceapp.data.network.feedback.FeedbackApiService
import com.example.sellingserviceapp.data.network.feedback.request.CreateFeedbackForBookingRequest
import com.example.sellingserviceapp.data.network.feedback.response.CreateFeedbackForBookingResponse
import com.example.sellingserviceapp.data.network.feedback.response.GetAvailableFeedbackResponse
import com.example.sellingserviceapp.data.network.feedback.response.GetFeedbackRatingResponse
import com.example.sellingserviceapp.data.network.feedback.response.GetFeedbackResponse
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class FeedbackRepository @Inject constructor(
    private val feedbackApiService: FeedbackApiService
): IFeedbackRepository {
    override suspend fun createFeedbackForBooking(
        bookingId: Int,
        request: CreateFeedbackForBookingRequest
    ): Result<CreateFeedbackForBookingResponse> {
        return try {
            val response = feedbackApiService.createFeedbackForBooking(bookingId, request)
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

    override suspend fun deleteFeedback(id: String): Result<com.example.sellingserviceapp.data.network.authorization.response.Response> {
        return try {
            val response = feedbackApiService.deleteFeedback(id)
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

    override suspend fun getAvailableFeedback(
        page: Int,
        size: Int
    ): Result<GetAvailableFeedbackResponse> {
        return try {
            val response = feedbackApiService.getAvailableFeedback(page, size)
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

    override suspend fun getFeedbackForService(
        serviceId: Int,
        page: Int,
        size: Int
    ): Result<GetFeedbackResponse> {
        return try {
            val response = feedbackApiService.getFeedbackForService(serviceId, page, size)
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

    override suspend fun getFeedbackRating(serviceId: Int): Result<GetFeedbackRatingResponse> {
        return try {
            val response = feedbackApiService.getFeedbackRating(serviceId)
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

    override suspend fun getUserFeedback(
        page: Int,
        size: Int
    ): Result<GetFeedbackResponse> {
        return try {
            val response = feedbackApiService.getUserFeedback(page, size)
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

    override suspend fun updateFeedback(
        id: String,
        request: CreateFeedbackForBookingRequest
    ): Result<CreateFeedbackForBookingResponse> {
        return try {
            val response = feedbackApiService.updateFeedback(id, request)
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
}