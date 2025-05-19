package com.example.sellingserviceapp.model.dto

import com.google.gson.annotations.SerializedName

data class PriceTypeDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val code: String
)