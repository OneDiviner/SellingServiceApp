package com.example.sellingserviceapp.data.network.gpt.repository

import com.example.sellingserviceapp.data.network.gpt.request.GenerateImageForServiceRequest
import com.example.sellingserviceapp.data.network.gpt.response.GenerateImageForServiceResponse

interface IGPTRepository {

    suspend fun generateImageForService(request: GenerateImageForServiceRequest) : Result<GenerateImageForServiceResponse>

    suspend fun getGeneratedImage(fileId: String) : Result<String>

}