package com.example.sellingserviceapp.data.network.authorization.request

import com.google.gson.annotations.SerializedName

data class VerifyResetPasswordCodeRequest(
    @SerializedName("email") val email: String,
    @SerializedName("code") val code: String
)