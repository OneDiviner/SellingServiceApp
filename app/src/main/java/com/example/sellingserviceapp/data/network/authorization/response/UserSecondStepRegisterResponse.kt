package com.example.sellingserviceapp.data.network.authorization.response

import com.google.gson.annotations.SerializedName

data class UserSecondStepRegisterResponse (
    @SerializedName("is_success") val isSuccess: Boolean,
    @SerializedName("message") val message: String,
)
