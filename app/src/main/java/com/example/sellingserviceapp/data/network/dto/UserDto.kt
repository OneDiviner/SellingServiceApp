package com.example.sellingserviceapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id") val id: Int,
    @SerializedName("email") val email: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("middle_name") val secondName: String,
    @SerializedName("last_name") val lastName: String?,
    @SerializedName("phone_number") val phoneNumber: String?,
    @SerializedName("avatar_path") val avatarPath: String?,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("role") val role: String,
    @SerializedName("status") val status: String
) {
    companion object {
        val EMPTY = UserDto(
            id = 0,
            email = "",
            firstName = "",
            secondName = "",
            lastName = "",
            phoneNumber = "",
            avatarPath = "",
            createdAt = "",
            updatedAt = "",
            role = "",
            status = ""
        )
    }
}