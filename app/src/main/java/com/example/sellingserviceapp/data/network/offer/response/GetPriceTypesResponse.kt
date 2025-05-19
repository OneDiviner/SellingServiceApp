package com.example.sellingserviceapp.data.network.offer.response

import com.example.sellingserviceapp.data.network.authorization.response.Response
import com.example.sellingserviceapp.model.dto.PriceTypeDto
import com.google.gson.annotations.SerializedName

data class GetPriceTypesResponse(
    @SerializedName("response") val response: Response,
    @SerializedName("price_types") val priceTypes: List<PriceTypeDto>
)