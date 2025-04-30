package com.example.sellingserviceapp.data.model.AuthApiResponse

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("is_success") val isSuccess: Boolean,
    @SerializedName("message") val message: String
)

data class ResetPassword(
    @SerializedName("token") val token: String,
    @SerializedName("expires_at") val expiresAt: String
)

data class VerifyResetPasswordCodeResponse(
    @SerializedName("response") val response: Response,
    @SerializedName("reset_password") val resetPassword: ResetPassword
)