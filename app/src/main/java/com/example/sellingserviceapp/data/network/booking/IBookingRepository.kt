package com.example.sellingserviceapp.data.network.booking

import okhttp3.ResponseBody

interface IBookingRepository {
    suspend fun getAvailableWorkTime(serviceId: Int, localDate: String): Result<List<String>>
    suspend fun createBooking(offerId: Int, startDateTime: String): Result<CreateBookingResponse>
    suspend fun getTimeTable(userId: Int): Result<List<Day>>
}