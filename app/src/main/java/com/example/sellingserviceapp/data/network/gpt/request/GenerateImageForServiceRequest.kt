package com.example.sellingserviceapp.data.network.gpt.request

import com.google.gson.annotations.SerializedName

data class GenerateImageForServiceRequest(
    @SerializedName("category") val category: String,
    @SerializedName("subcategory") val subcategory: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String
)