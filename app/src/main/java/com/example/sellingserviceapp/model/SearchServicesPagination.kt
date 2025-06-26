package com.example.sellingserviceapp.model

import com.example.sellingserviceapp.data.network.authorization.response.Response
import com.example.sellingserviceapp.data.network.offer.response.Pageable
import com.example.sellingserviceapp.model.domain.ServiceDomain
import com.example.sellingserviceapp.model.dto.ServiceDto
import com.google.gson.annotations.SerializedName

data class SearchServicesPagination(
    @SerializedName("response") val response: Response,
    @SerializedName("offers") val services: List<ServiceDomain>?,
    @SerializedName("pageable") val pageable: Pageable,
)