package com.example.sellingserviceapp.data.model.offerApiResponse

import com.example.sellingserviceapp.data.model.AuthApiResponse.Response
import com.google.gson.annotations.SerializedName

data class PriceTypes(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)
data class GetPriceTypesResponse(
    @SerializedName("response") val response: Response,
    @SerializedName("price_types") val priceTypes: List<PriceTypes>
)