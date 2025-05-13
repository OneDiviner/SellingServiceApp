package com.example.sellingserviceapp.data.network.authorization.request

data class SendCodeToVerificationRequest(
    val email: String,
    val code: String
)