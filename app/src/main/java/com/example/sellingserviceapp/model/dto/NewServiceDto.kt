package com.example.sellingserviceapp.model.dto

import com.google.gson.annotations.SerializedName

data class NewServiceDto(
    @SerializedName("title") val tittle: String,
    @SerializedName("description") val description: String,
    @SerializedName("duration") val duration: Int,
    @SerializedName("location") val address: String,
    @SerializedName("price") val price: Int,
    @SerializedName("price_type_id") val priceTypeId: Int,
    @SerializedName("subcategory_id") val subcategoryId: Int,
    @SerializedName("location_type_ids") val locationTypeIds: List<Int>
)