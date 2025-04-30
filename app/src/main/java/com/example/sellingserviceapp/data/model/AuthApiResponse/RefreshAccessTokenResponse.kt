package com.example.sellingserviceapp.data.model.AuthApiResponse

import com.google.gson.annotations.SerializedName

data class RefreshAccessTokenResponse (
    @SerializedName("response") val response: Response,
    @SerializedName("access") val access: Access
)