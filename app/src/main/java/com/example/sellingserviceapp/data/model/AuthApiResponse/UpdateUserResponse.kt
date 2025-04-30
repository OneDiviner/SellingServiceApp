package com.example.sellingserviceapp.data.model.AuthApiResponse

import com.google.gson.annotations.SerializedName

data class UpdateUserResponse(
    @SerializedName("response") val response: Response,
    @SerializedName("user") val user: User
)