package com.example.sellingserviceapp.data.network.feedback.request

import com.google.gson.annotations.SerializedName

data class CreateFeedbackForBookingRequest(
    @SerializedName("comment") val comment: String,
    @SerializedName("rating") val rating: Int
)