package com.example.sellingserviceapp.data.network.feedback.response

import com.example.sellingserviceapp.data.network.authorization.response.Response
import com.example.sellingserviceapp.data.network.feedback.model.FeedbackDto
import com.example.sellingserviceapp.data.network.offer.response.Pageable
import com.google.gson.annotations.SerializedName

data class GetFeedbackResponse(
    @SerializedName("response") val response: Response,
    @SerializedName("feedback") val feedbackDtoList: List<FeedbackDto>,
    @SerializedName("pageable") val pageable: Pageable
)