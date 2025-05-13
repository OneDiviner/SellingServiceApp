package com.example.sellingserviceapp.data.network.authorization.request

import com.google.gson.annotations.SerializedName

data class SendVerificationResetPasswordCodeRequest(
    @SerializedName("email") val email: String
)