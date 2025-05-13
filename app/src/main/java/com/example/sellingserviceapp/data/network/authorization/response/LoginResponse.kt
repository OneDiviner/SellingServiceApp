package com.example.sellingserviceapp.data.network.authorization.response

import com.google.gson.annotations.SerializedName

data class Access(
    @SerializedName("token") val token: String,
    @SerializedName("expires_at") val expiresAt: String,
)

data class Refresh(
    @SerializedName("token") val token: String,
    @SerializedName("expires_at") val expiresAt: String,
)

data class LoginResponse(
    @SerializedName("response") val response: Response,
    @SerializedName("access") val access: Access,
    @SerializedName("refresh") val refresh: Refresh,
)