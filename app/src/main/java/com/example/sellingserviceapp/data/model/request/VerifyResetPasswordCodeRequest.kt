package com.example.sellingserviceapp.data.model.request

import com.google.gson.annotations.SerializedName

data class VerifyResetPasswordCodeRequest(
    @SerializedName("email") val email: String,
    @SerializedName("code") val code: String
)