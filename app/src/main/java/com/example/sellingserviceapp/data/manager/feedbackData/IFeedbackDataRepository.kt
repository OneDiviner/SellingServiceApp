package com.example.sellingserviceapp.data.manager.feedbackData

import com.example.sellingserviceapp.data.network.authorization.response.Response
import com.example.sellingserviceapp.data.network.feedback.model.AvailableFeedbacks
import com.example.sellingserviceapp.data.network.feedback.model.FeedbackDto
import com.example.sellingserviceapp.data.network.feedback.request.CreateFeedbackForBookingRequest
import com.example.sellingserviceapp.data.network.feedback.response.CreateFeedbackForBookingResponse
import com.example.sellingserviceapp.data.network.feedback.response.GetAvailableFeedbackResponse
import com.example.sellingserviceapp.data.network.feedback.response.GetFeedbackRatingResponse
import com.example.sellingserviceapp.data.network.feedback.response.GetFeedbackResponse
import com.example.sellingserviceapp.model.FeedbackWithData

interface IFeedbackDataRepository {
    suspend fun createFeedbackForBooking(bookingId: Int, comment: String, rating: Int) : FeedbackDto

    suspend fun updateFeedback(bookingId: String, comment: String, rating: Int) : FeedbackDto

    suspend fun deleteFeedback(bookingId: String)

    suspend fun getUserFeedback(page: Int = 0, size: Int = 10) : List<FeedbackDto>

    suspend fun getFeedbackForService(serviceId: Int = 0, page: Int = 0, size: Int = 10) : List<FeedbackDto>

    suspend fun getFeedbackRating(serviceId: Int) : Double

    suspend fun getAvailableFeedback(page: Int = 0, size: Int = 10) : List<AvailableFeedbacks>
}

/*
suspend fun createFeedbackForBooking( bookingId: Int, request: CreateFeedbackForBookingRequest) : Result<CreateFeedbackForBookingResponse>

suspend fun updateFeedback( id: String, request: CreateFeedbackForBookingRequest) : Result<CreateFeedbackForBookingResponse>

suspend fun deleteFeedback(id: String) : Result<Response>

suspend fun getUserFeedback(page: Int, size: Int = 10, ) : Result<GetFeedbackResponse>

suspend fun getFeedbackForService(serviceId: Int = 0, page: Int = 0, size: Int = 10) : Result<GetFeedbackResponse>

suspend fun getFeedbackRating(serviceId: Int) : Result<GetFeedbackRatingResponse>

suspend fun getAvailableFeedback(page: Int, size: Int = 10, ) : Result<GetAvailableFeedbackResponse>*/
