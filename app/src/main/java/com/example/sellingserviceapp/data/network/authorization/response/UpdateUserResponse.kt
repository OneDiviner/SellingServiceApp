package com.example.sellingserviceapp.data.network.authorization.response

import com.example.sellingserviceapp.data.network.dto.UserDto
import com.google.gson.annotations.SerializedName

data class UpdateUserResponse(
    @SerializedName("response") val response: Response,
    @SerializedName("user") val userDto: UserDto
)