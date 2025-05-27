package com.example.sellingserviceapp.data.network.offer.response

import com.example.sellingserviceapp.data.network.authorization.response.Response
import com.google.gson.annotations.SerializedName

data class CreateServiceResponse(
    @SerializedName("response") val response: Response,
    @SerializedName("id") val serviceId: Int
)