package com.example.sellingserviceapp.data.network.feedback.response

import com.example.sellingserviceapp.data.network.authorization.response.Response
import com.google.gson.annotations.SerializedName

data class GetFeedbackRatingResponse(
    @SerializedName("response") val response: Response,
    @SerializedName("rating") val rating: Double
)