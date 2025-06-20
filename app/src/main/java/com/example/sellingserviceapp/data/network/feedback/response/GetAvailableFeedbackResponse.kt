package com.example.sellingserviceapp.data.network.feedback.response

import com.example.sellingserviceapp.data.network.authorization.response.Response
import com.example.sellingserviceapp.data.network.feedback.model.AvailableFeedbacks
import com.example.sellingserviceapp.data.network.offer.response.Pageable
import com.google.gson.annotations.SerializedName

data class GetAvailableFeedbackResponse(
    @SerializedName("response") val response: Response,
    @SerializedName("available_feedbacks") val availableFeedbacks: List<AvailableFeedbacks>,
    @SerializedName("pageable") val pageable: Pageable
)