package com.example.sellingserviceapp.data.network.offer.response

import com.example.sellingserviceapp.data.network.authorization.response.Response
import com.example.sellingserviceapp.model.dto.ServiceDto
import com.google.gson.annotations.SerializedName


data class GetServiceResponse(
    @SerializedName("response") val response: Response,
    @SerializedName("offer") val serviceDto: ServiceDto
)