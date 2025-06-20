package com.example.sellingserviceapp.data.network.gpt

import com.example.sellingserviceapp.data.network.gpt.request.GenerateImageForServiceRequest
import com.example.sellingserviceapp.data.network.gpt.response.GenerateImageForServiceResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GPTApiService {

    @POST("/api/private/gpt/image/generate-for-offer")
    suspend fun generateImageForService(@Body request: GenerateImageForServiceRequest) : Response<GenerateImageForServiceResponse>

    @GET("/api/private/gpt/image/{fileId}")
    suspend fun getGeneratedImage(@Path("fileId") fileId: String) : Response<ResponseBody>

}