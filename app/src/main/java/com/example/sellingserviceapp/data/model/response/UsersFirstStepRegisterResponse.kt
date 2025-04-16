package com.example.sellingserviceapp.data.model.response

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class UsersFirstStepRegisterResponse(
    @SerializedName("is_success") val isSuccess: Boolean,
    @SerializedName("message") val message: String,

)