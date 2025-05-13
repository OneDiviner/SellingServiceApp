package com.example.sellingserviceapp.data.network.authorization.response

import com.google.gson.annotations.SerializedName

data class RefreshAccessTokenResponse (
    @SerializedName("response") val response: Response,
    @SerializedName("access") val access: Access
)