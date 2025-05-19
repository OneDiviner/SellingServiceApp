package com.example.sellingserviceapp.data.network.offer.response

import com.example.sellingserviceapp.model.dto.CategoryDto
import com.google.gson.annotations.SerializedName

data class GetCategoriesResponse(
    @SerializedName("is_success") val isSuccess: String,
    @SerializedName("categories") val categories: List<CategoryDto>,
)