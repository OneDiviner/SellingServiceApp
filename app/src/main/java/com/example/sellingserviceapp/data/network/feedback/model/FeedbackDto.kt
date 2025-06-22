package com.example.sellingserviceapp.data.network.feedback.model

import com.google.gson.annotations.SerializedName

data class FeedbackDto(
    @SerializedName("id") val id: String,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("offer_id") val offerId: Int,
    @SerializedName("booking_id") val bookingId: Int,
    @SerializedName("is_user_deleted") val isUserDeleted: Boolean,
    @SerializedName("is_offer_deleted") val isOfferDeleted: Boolean,
    @SerializedName("comment") val comment: String,
    @SerializedName("rating") val rating: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
) {
    companion object {
        val TEST_POSITIVE = FeedbackDto(
            id = "feedback_12345",
            userId = 789,
            offerId = 456,
            bookingId = 123,
            isUserDeleted = false,
            isOfferDeleted = false,
            comment = "Отличный сервис! Мастер приехал вовремя и качественно выполнил работу.",
            rating = 5,
            createdAt = "2023-05-15T14:30:00Z",
            updatedAt = "2023-05-15T14:30:00Z"
        )

        val TEST_NEGATIVE = FeedbackDto(
            id = "feedback_67890",
            userId = 321,
            offerId = 654,
            bookingId = 987,
            isUserDeleted = true,
            isOfferDeleted = false,
            comment = "Не понравилось качество работы, пришлось переделывать.",
            rating = 2,
            createdAt = "2023-06-20T10:15:00Z",
            updatedAt = "2023-06-21T09:45:00Z"
        )

        val EMPTY = FeedbackDto(
            id = "",
            userId = 0,
            offerId = 0,
            bookingId = 0,
            isUserDeleted = false,
            isOfferDeleted = false,
            comment = "",
            rating = 0,
            createdAt = "",
            updatedAt = ""
        )
    }
}