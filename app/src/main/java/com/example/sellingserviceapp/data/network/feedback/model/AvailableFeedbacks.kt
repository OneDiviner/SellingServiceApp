package com.example.sellingserviceapp.data.network.feedback.model

import com.google.gson.annotations.SerializedName

data class AvailableFeedbacks(
    @SerializedName("offer_id") val serviceId: Int,
    @SerializedName("booking_id") val bookingId: Int
)