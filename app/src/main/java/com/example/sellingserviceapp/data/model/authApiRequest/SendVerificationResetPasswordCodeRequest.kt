package com.example.sellingserviceapp.data.model.authApiRequest

import com.google.gson.annotations.SerializedName

data class SendVerificationResetPasswordCodeRequest(
    @SerializedName("email") val email: String
)