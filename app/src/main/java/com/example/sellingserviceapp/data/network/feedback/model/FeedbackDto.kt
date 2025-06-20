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
)