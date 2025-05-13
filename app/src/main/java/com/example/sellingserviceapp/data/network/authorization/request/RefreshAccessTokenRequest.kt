package com.example.sellingserviceapp.data.network.authorization.request

import com.google.gson.annotations.SerializedName

data class RefreshAccessTokenRequest(
    @SerializedName("refresh_token") val refreshToken: String
)