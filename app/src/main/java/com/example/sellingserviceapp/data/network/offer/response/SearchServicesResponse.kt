package com.example.sellingserviceapp.data.network.offer.response

import com.example.sellingserviceapp.data.network.authorization.response.Response
import com.google.gson.annotations.SerializedName

data class ShortServiceData(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("photo_path") val photoPath: String,
    @SerializedName("status") val status: String,
    @SerializedName("category") val category: String,
    @SerializedName("subcategory") val subcategory: String
)

data class Pageable(
    @SerializedName("page") val page: Int,
    @SerializedName("page_max") val pageMax: Int,
    @SerializedName("size") val size: String
)

data class SearchServicesResponse(
    @SerializedName("response") val response: Response,
    @SerializedName("offers") val services: List<ShortServiceData>,
    @SerializedName("pageable") val pageable: Pageable
)