package com.example.sellingserviceapp.data.model.response

import com.example.sellingserviceapp.data.di.SecureTokenStorage
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