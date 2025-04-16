package com.example.sellingserviceapp.data.model.request

import com.google.gson.annotations.SerializedName

data class RefreshPasswordRequest(
    @SerializedName("reset_password_token") val resetPasswordToken: String,
    @SerializedName("password") val password: String
)