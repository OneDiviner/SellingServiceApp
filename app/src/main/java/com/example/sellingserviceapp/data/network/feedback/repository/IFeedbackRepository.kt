package com.example.sellingserviceapp.data.network.feedback.repository

import com.example.sellingserviceapp.data.network.authorization.response.Response
import com.example.sellingserviceapp.data.network.feedback.request.CreateFeedbackForBookingRequest
import com.example.sellingserviceapp.data.network.feedback.response.CreateFeedbackForBookingResponse
import com.example.sellingserviceapp.data.network.feedback.response.GetAvailableFeedbackResponse
import com.example.sellingserviceapp.data.network.feedback.response.GetFeedbackRatingResponse
import com.example.sellingserviceapp.data.network.feedback.response.GetFeedbackResponse

interface IFeedbackRepository {

    suspend fun createFeedbackForBooking( bookingId: Int, request: CreateFeedbackForBookingRequest) : Result<CreateFeedbackForBookingResponse>

    suspend fun updateFeedback( id: String, request: CreateFeedbackForBookingRequest) : Result<CreateFeedbackForBookingResponse>

    suspend fun deleteFeedback(id: String) : Result<Response>

    suspend fun getUserFeedback(page: Int, size: Int = 10, ) : Result<GetFeedbackResponse>

    suspend fun getFeedbackForService(serviceId: Int = 0, page: Int = 0, size: Int = 10) : Result<GetFeedbackResponse>

    suspend fun getFeedbackRating(serviceId: Int) : Result<GetFeedbackRatingResponse>

    suspend fun getAvailableFeedback(page: Int, size: Int = 10, ) : Result<GetAvailableFeedbackResponse>
}