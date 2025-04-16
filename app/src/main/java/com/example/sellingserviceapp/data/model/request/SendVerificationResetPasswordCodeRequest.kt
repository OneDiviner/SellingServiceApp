package com.example.sellingserviceapp.data.model.request

import com.google.gson.annotations.SerializedName

data class SendVerificationResetPasswordCodeRequest(
    @SerializedName("email") val email: String
)