package com.example.sellingserviceapp.data.model.response

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: String,
    @SerializedName("email") val email: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("middle_name") val middleName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("avatar_base64") val avatar: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("role") val role: String,
    @SerializedName("status") val status: String,
)

data class GetUserResponse(
    @SerializedName("response") val response: Response,
    @SerializedName("user") val user: User
)