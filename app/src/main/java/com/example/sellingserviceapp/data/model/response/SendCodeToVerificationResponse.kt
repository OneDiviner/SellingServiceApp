package com.example.sellingserviceapp.data.model.response

import com.google.gson.annotations.SerializedName

data class SendCodeToVerificationResponse(
    @SerializedName("is_success") val isSuccess: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("email_token") val emailToken: String
)