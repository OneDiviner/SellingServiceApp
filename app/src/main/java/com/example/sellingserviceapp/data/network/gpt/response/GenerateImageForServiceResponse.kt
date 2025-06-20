package com.example.sellingserviceapp.data.network.gpt.response

import com.example.sellingserviceapp.data.network.authorization.response.Response
import com.google.gson.annotations.SerializedName

data class GenerateImageForServiceResponse(
    @SerializedName("response") val response: Response,
    @SerializedName("file_id") val fileId: String
)