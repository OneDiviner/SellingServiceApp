package com.example.sellingserviceapp.data.network.booking

import com.example.sellingserviceapp.ui.screen.profile.NewWorkTime


interface IBookingRepository {
    suspend fun getAvailableWorkTime(serviceId: Int, localDate: String): Result<List<String>>
    suspend fun createBooking(offerId: Int, startDateTime: String): Result<CreateBookingResponse>
    suspend fun getTimeTable(userId: Int): Result<List<Day>>
    suspend fun getUserTimeTable(): Result<TimeTable>
    suspend fun updateWorkTime(newWorkTime: NewWorkTime): Result<TimeTable>
    suspend fun getBookingAsExecutor(page: Int, size: Int, statusId: Int? = null): Result<GetBookingResponse>
    suspend fun getBookingAsExecutor(date: String): Result<GetBookingResponse>
    suspend fun getBookingAsClient(page: Int, size: Int, statusId: Int? = null): Result<GetBookingResponse>
    suspend fun getBookingAsClient(date: String): Result<GetBookingResponse>
    suspend fun confirmBookingAsExecutor(bookingId: Int): Result<CreateBookingResponse>
    suspend fun rejectBookingAsExecutor(bookingId: Int): Result<CreateBookingResponse>
    suspend fun getBookingStatuses(): Result<GetBookingStatusesResponse>
}