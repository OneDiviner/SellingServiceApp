package com.example.sellingserviceapp.data.network.offer.response

import com.google.gson.annotations.SerializedName

data class Subcategories(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

data class GetSubcategoriesResponse(
    @SerializedName("is_success") val isSuccess: String,
    @SerializedName("subcategories") val subcategories: List<Subcategories>,
)