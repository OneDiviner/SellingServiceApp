package com.example.sellingserviceapp.data.model.authApiRequest

import com.google.gson.annotations.SerializedName

data class RefreshAccessTokenRequest(
    @SerializedName("refresh_token") val refreshToken: String
)