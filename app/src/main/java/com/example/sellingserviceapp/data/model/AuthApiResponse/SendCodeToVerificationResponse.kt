package com.example.sellingserviceapp.data.model.AuthApiResponse

import com.google.gson.annotations.SerializedName

data class Email(
    @SerializedName("token") val token: String,
    @SerializedName("expires_at") val expiresAt: String
)

data class SendCodeToVerificationResponse(
    @SerializedName("response") val response: Response,
    @SerializedName("email") val email: Email
)