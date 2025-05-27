package com.example.sellingserviceapp.data.network.booking

import com.example.sellingserviceapp.data.network.authorization.response.Response
import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

data class AvailableWorkTimeResponse(
    @SerializedName("response") val response: Response,
    @SerializedName("slots") val slots: List<String>
)

data class CreateBookingRequest(
    @SerializedName("offer_id") val offerId: Int,
    @SerializedName("start_date_time") val startDateTime: String
)

data class Day(
    @SerializedName("id") val id: Long, // Используем Long для таких больших чисел, как 9007199254740991
    @SerializedName("name") val name: String
)

data class TimeTableResponse(
    @SerializedName("response") val response: Response,
    @SerializedName("work_time") val timeTable: TimeTable
)

// Модель для объекта "work_time"
data class TimeTable(
    @SerializedName("id") val id: Long,
    @SerializedName("user_id") val userId: Long,
    @SerializedName("start_time") val startTime: String,
    @SerializedName("end_time") val endTime: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("days") val days: List<Day>
)


data class Booking(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("offer_id") val offerId: Int,
    @SerializedName("start_date_time") val startDateTime: String,
    @SerializedName("end_date_time") val endDateTime: String,
    @SerializedName("is_user_deleted") val isUserDeleted: Boolean,
    @SerializedName("is_offer_deleted") val isOfferDeleted: Boolean,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("status") val status: String,
    @SerializedName("status_reason") val statusReason: String
)

data class CreateBookingResponse(
    @SerializedName("response") val response: Response,
    @SerializedName("booking") val booking: Booking
)

interface BookingApiService {
    @Headers("Token: true")
    @GET("/api/private/work-times/available/{offerId}")
    suspend fun getAvailableWorkTime(
        @Path("offerId") serviceId: Int,
        @Query("localDate") localDate: String
    ): retrofit2.Response<AvailableWorkTimeResponse>

    @Headers("Token: true")
    @POST("/api/private/booking/create")
    suspend fun createBooking(
        @Body request: CreateBookingRequest
    ): retrofit2.Response<CreateBookingResponse>

    @Headers("Token: true")
    @GET("/api/private/work-times/{userId}")
    suspend fun getTimeTable(@Path("userId") userId: Int): retrofit2.Response<TimeTableResponse>
}