package com.example.sellingserviceapp.data.model.request

data class SendCodeToVerificationRequest(
    val email: String,
    val code: String
)