package com.example.sellingserviceapp.data.network.offer.response

import com.example.sellingserviceapp.data.network.authorization.response.Response
import com.google.gson.annotations.SerializedName

data class GetLocationType(
    @SerializedName("name") val name: String,
    @SerializedName("location") val location: String
)

data class Service(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val user_id: Int,
    @SerializedName("title") val tittle: String,
    @SerializedName("description") val description: String,
    @SerializedName("duration") val duration: Int,
    @SerializedName("photo_path") val photoPath: String,
    @SerializedName("price") val price: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("location_types") val locationTypes: List<GetLocationType>,
    @SerializedName("price_type") val priceType: String,
    @SerializedName("status") val status: String,
    @SerializedName("category") val category: String,
    @SerializedName("subcategory") val subcategory: String,
)

data class GetServiceResponse(
    @SerializedName("response") val response: Response,
    @SerializedName("offer") val service: Service
)