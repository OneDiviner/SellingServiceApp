package com.example.sellingserviceapp.data.network.feedback

import com.example.sellingserviceapp.data.network.feedback.request.CreateFeedbackForBookingRequest
import com.example.sellingserviceapp.data.network.feedback.response.CreateFeedbackForBookingResponse
import com.example.sellingserviceapp.data.network.feedback.response.GetAvailableFeedbackResponse
import com.example.sellingserviceapp.data.network.feedback.response.GetFeedbackRatingResponse
import com.example.sellingserviceapp.data.network.feedback.response.GetFeedbackResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FeedbackApiService {

    @POST("/api/private/feedback/create/{bookingId}")
    suspend fun createFeedbackForBooking(
        @Path("bookingId") bookingId: Int,
        @Body request: CreateFeedbackForBookingRequest
    ) : Response<CreateFeedbackForBookingResponse>

    @PATCH("/api/private/feedback/update/{id}")
    suspend fun updateFeedback(
        @Path("id") id: String,
        @Body request: CreateFeedbackForBookingRequest
    ) : Response<CreateFeedbackForBookingResponse>

    @DELETE("/api/private/feedback/delete/{id}")
    suspend fun deleteFeedback(
        @Path("id") id: String,
    ) : Response<com.example.sellingserviceapp.data.network.authorization.response.Response>

    @GET("/api/private/feedback")
    suspend fun getUserFeedback(
        @Query("page") page: Int,
        @Query("size") size: Int = 10,
    ) : Response<GetFeedbackResponse>

    @GET("/api/private/feedback/{offerId}")
    suspend fun getFeedbackForService(
        @Query("offerId") serviceId: Int = 0,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10,
    ) : Response<GetFeedbackResponse>

    @GET("/api/private/feedback/rating/{offerId}")
    suspend fun getFeedbackRating(
        @Query("offerId") serviceId: Int
    ) : Response<GetFeedbackRatingResponse>

    @GET("/api/private/available-feedbacks")
    suspend fun getAvailableFeedback(
        @Query("page") page: Int,
        @Query("size") size: Int = 10,
    ) : Response<GetAvailableFeedbackResponse>

}