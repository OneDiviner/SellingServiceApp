package com.example.sellingserviceapp.data.network.offer.response

import com.example.sellingserviceapp.data.network.authorization.response.Response
import com.example.sellingserviceapp.model.dto.ServiceDto
import com.google.gson.annotations.SerializedName

data class SearchUserServiceResponse(
    @SerializedName("response") val response: Response,
    @SerializedName("offers") val services: List<ServiceDto>,
    @SerializedName("pageable") val pageable: Pageable
)