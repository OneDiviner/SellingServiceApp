package com.example.sellingserviceapp.data.network.feedback.response

import com.example.sellingserviceapp.data.network.authorization.response.Response
import com.example.sellingserviceapp.data.network.feedback.model.FeedbackDto
import com.google.gson.annotations.SerializedName

data class CreateFeedbackForBookingResponse(
    @SerializedName("response") val response: Response,
    @SerializedName("feedback") val feedbackDto: FeedbackDto
)