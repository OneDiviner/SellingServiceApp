package com.example.sellingserviceapp.data.model.response

import com.google.gson.annotations.SerializedName

data class UserStatusResponse(
    @SerializedName("is_success") val isSuccess: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: String
)