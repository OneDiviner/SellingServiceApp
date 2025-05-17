package com.example.sellingserviceapp.model.dto

import com.google.gson.annotations.SerializedName

data class ServiceDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("user_id")
    val userId: Int,

    @SerializedName("title")
    val tittle: String,

    @SerializedName("description")
    val description: String?,

    @SerializedName("duration")
    val duration: Int?,

    @SerializedName("photo_path")
    val photoPath: String?,

    @SerializedName("price")
    val price: Int?,

    @SerializedName("created_at")
    val createdAt: String?,

    @SerializedName("updated_at")
    val updatedAt: String?,

    @SerializedName("location_types")
    val formats: List<FormatsDto>?,

    @SerializedName("price_type")
    val priceType: String?,

    @SerializedName("status")
    val status: String,

    @SerializedName("category")
    val category: String,

    @SerializedName("subcategory")
    val subcategory: String

)