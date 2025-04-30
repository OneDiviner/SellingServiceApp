package com.example.sellingserviceapp.data.model.authApiRequest

data class SendCodeToVerificationRequest(
    val email: String,
    val code: String
)