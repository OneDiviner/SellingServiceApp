package com.example.sellingserviceapp.data.network.booking

import com.example.sellingserviceapp.data.network.AuthApiError
import com.example.sellingserviceapp.data.network.authorization.request.FirstStepRegisterRequest
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class BookingRepository @Inject constructor(
    private val bookingApiService: BookingApiService
): IBookingRepository {
    override suspend fun getAvailableWorkTime(serviceId: Int, localDate: String): Result<List<String>> {
        return try {
            val response = bookingApiService.getAvailableWorkTime(serviceId, localDate)

            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it.slots)
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

    override suspend fun createBooking(offerId: Int, startDateTime: String): Result<CreateBookingResponse> {
        return try {
            val response = bookingApiService.createBooking(CreateBookingRequest(offerId, startDateTime))

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

    override suspend fun getTimeTable(userId: Int): Result<List<Day>> {
        return try {
            val response = bookingApiService.getTimeTable(userId)

            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it.timeTable.days)
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