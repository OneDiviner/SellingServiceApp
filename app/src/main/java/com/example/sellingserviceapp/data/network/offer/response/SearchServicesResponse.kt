package com.example.sellingserviceapp.data.network.offer.response

import com.example.sellingserviceapp.data.network.authorization.response.Response
import com.example.sellingserviceapp.model.dto.ServiceDto
import com.google.gson.annotations.SerializedName

data class Pageable(
    @SerializedName("page") val page: Int,
    @SerializedName("page_max") val pageMax: Int,
    @SerializedName("size") val size: String
)

data class SearchServicesResponse(
    @SerializedName("response") val response: Response,
    @SerializedName("offers") val services: List<ServiceDto>?,
    @SerializedName("pageable") val pageable: Pageable
)