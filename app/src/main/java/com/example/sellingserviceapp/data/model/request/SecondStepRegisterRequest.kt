package com.example.sellingserviceapp.data.model.request

import com.google.gson.annotations.SerializedName

data class SecondStepRegisterRequest (
    @SerializedName("email_token") val emailToken: String,
    @SerializedName("first_name")val firstName: String,
    @SerializedName("middle_name")val secondName: String,
    @SerializedName("last_name")val lastName: String,
    @SerializedName("phone_number")val phoneNumber: String
)