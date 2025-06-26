package com.example.sellingserviceapp.data.network.authorization.request

import com.google.gson.annotations.SerializedName

data class UpdateUserRequest(
    @SerializedName("password") val password: String?,
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("last_name") val middleName: String?,
    @SerializedName("middle_name") val lastName: String?,
    @SerializedName("phone_number") val phoneNumber: String?
)