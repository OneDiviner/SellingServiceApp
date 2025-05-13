package com.example.sellingserviceapp.data.network.offer.response

import com.google.gson.annotations.SerializedName

data class Categories(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

data class GetCategoriesResponse(
    @SerializedName("is_success") val isSuccess: String,
    @SerializedName("categories") val categories: List<Categories>,
)