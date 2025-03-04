package com.example.sellingserviceapp.data.model.response

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class UsersFirstStepRegisterResponse(
    @SerializedName("is_success") val isSuccess: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("id") val id: Long,
    @SerializedName("email") val email: String,
    @SerializedName("created_at") val createdAt: String, // LocalDataTime
    @SerializedName("updated_at") val updatedAt: String, // LocalDataTime
    @SerializedName("role") val role: String,
    @SerializedName("status") val status: String
)