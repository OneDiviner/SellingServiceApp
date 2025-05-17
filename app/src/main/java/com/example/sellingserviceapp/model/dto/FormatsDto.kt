package com.example.sellingserviceapp.model.dto

import com.google.gson.annotations.SerializedName

data class FormatsDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val code: String,
    @SerializedName("location") val address: String?,
    @SerializedName("is_physical") val isPhysical: Boolean
)
