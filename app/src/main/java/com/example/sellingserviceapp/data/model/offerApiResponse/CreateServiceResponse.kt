package com.example.sellingserviceapp.data.model.offerApiResponse

import com.example.sellingserviceapp.data.model.AuthApiResponse.Response
import com.google.gson.annotations.SerializedName

data class CreateServiceResponse(
    @SerializedName("response") val response: Response,
    @SerializedName("id") val id: Int,
)