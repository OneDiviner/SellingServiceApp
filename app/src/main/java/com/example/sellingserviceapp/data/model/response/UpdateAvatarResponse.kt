package com.example.sellingserviceapp.data.model.response

import com.google.gson.annotations.SerializedName

data class UpdateAvatarResponse(
    @SerializedName("is_success") val isSuccess: String,
    @SerializedName("message") val message: String
)