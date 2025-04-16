package com.example.sellingserviceapp.data.model.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("is_success") val isSuccess: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("access_token_expires_at") val accessTokenExpiresAt: String,
    @SerializedName("refresh_token_expires_at") val refreshTokenExpiresAt: String,
)