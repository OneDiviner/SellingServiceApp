package com.example.sellingserviceapp.data.model.authApiRequest

import com.google.gson.annotations.SerializedName

data class UpdateUserRequest(
    @SerializedName("password") val password: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("middle_name") val middleName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("phone_number") val phoneNumber: String
)