package com.example.sellingserviceapp.data.model.response

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class UsersFirstStepRegisterResponse(
    @SerializedName("is_success") val isSuccess: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("user") val user: User

)

data class User(
    @SerializedName("id") val id: Long,
    @SerializedName("email") val email: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("middle_name") val middleName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("created_at") val createdAt: String, // LocalDateTime
    @SerializedName("updated_at") val updatedAt: String, // Date
    @SerializedName("role") val role: String,
    @SerializedName("status") val status: String
)