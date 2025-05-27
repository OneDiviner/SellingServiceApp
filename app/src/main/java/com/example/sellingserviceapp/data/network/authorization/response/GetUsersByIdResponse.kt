package com.example.sellingserviceapp.data.network.authorization.response

import com.example.sellingserviceapp.model.dto.UserDto
import com.google.gson.annotations.SerializedName

data class GetUserByIdResponse(
    @SerializedName("response") val response: Response,
    @SerializedName("users") val listUsersDto: List<UserDto>
)