package com.example.sellingserviceapp.data.network.offer.response

import com.example.sellingserviceapp.model.dto.SubcategoryDto
import com.google.gson.annotations.SerializedName

data class GetSubcategoriesResponse(
    @SerializedName("is_success") val isSuccess: String,
    @SerializedName("subcategories") val subcategories: List<SubcategoryDto>,
)