package com.example.sellingserviceapp.data.manager.feedbackData

import android.util.Log
import com.example.sellingserviceapp.data.network.booking.CreateBookingRequest
import com.example.sellingserviceapp.data.network.feedback.model.AvailableFeedbacks
import com.example.sellingserviceapp.data.network.feedback.model.FeedbackDto
import com.example.sellingserviceapp.data.network.feedback.repository.FeedbackRepository
import com.example.sellingserviceapp.data.network.feedback.request.CreateFeedbackForBookingRequest
import com.example.sellingserviceapp.model.FeedbackWithData
import javax.inject.Inject

class FeedbackDataRepository @Inject constructor(
    private val feedbackRepository: FeedbackRepository
) : IFeedbackDataRepository {
    override suspend fun createFeedbackForBooking(
        bookingId: Int,
        comment: String,
        rating: Int
    ): FeedbackDto {
        val createFeedbackResponse = feedbackRepository.createFeedbackForBooking(
            bookingId = bookingId,
            request = CreateFeedbackForBookingRequest(
                comment = comment,
                rating = rating
            )
        )
        createFeedbackResponse.onSuccess { response ->
            return response.feedbackDto
        }
        return FeedbackDto.EMPTY
    }

    override suspend fun deleteFeedback(feedbackId: String) {
        val deleteFeedback = feedbackRepository.deleteFeedback(feedbackId)
        deleteFeedback.onSuccess {

        }.onFailure {

        }
    }

    override suspend fun getAvailableFeedback(
        page: Int,
        size: Int
    ): List<AvailableFeedbacks> {
        val getAvailableFeedbacks = feedbackRepository.getAvailableFeedback(page, size)
        getAvailableFeedbacks.onSuccess {
            return it.availableFeedbacks
        }
        return emptyList()
    }

    override suspend fun getFeedbackForService(
        serviceId: Int,
        page: Int,
        size: Int
    ): List<FeedbackDto> {
        Log.d("GET_FEEDBACK_FOR_SERVICE", serviceId.toString())
        val getFeedbackResponse = feedbackRepository.getFeedbackForService(serviceId, page, size)
        getFeedbackResponse.onSuccess { response ->
            return response.feedbackDtoList ?: emptyList()
        }
        return emptyList()
    }

    override suspend fun getFeedbackRating(serviceId: Int): Double {
        Log.d("SERVICE_VIEW_MODEL_RATING", serviceId.toString())
        val getRatingResponse = feedbackRepository.getFeedbackRating(serviceId)
        getRatingResponse.onSuccess {
            return it.rating
        }
        return 0.0
    }

    override suspend fun getUserFeedback(
        page: Int,
        size: Int
    ): List<FeedbackDto> {
        val getFeedbackResponse = feedbackRepository.getUserFeedback(page, size)
        getFeedbackResponse.onSuccess { response ->
            return response.feedbackDtoList ?: emptyList()
        }
        return emptyList()
    }

    override suspend fun updateFeedback(
        bookingId: String,
        comment: String,
        rating: Int
    ): FeedbackDto {
        val updateFeedbackResponse = feedbackRepository.updateFeedback(
            bookingId,
            CreateFeedbackForBookingRequest(
                comment,
                rating
            )
        )
        updateFeedbackResponse.onSuccess {
            return it.feedbackDto
        }
        return FeedbackDto.EMPTY
    }
}