package com.example.sellingserviceapp.data.network

import com.example.sellingserviceapp.data.model.offerApiRequest.CreateServiceRequest
import com.example.sellingserviceapp.data.model.offerApiResponse.CreateServiceResponse
import com.example.sellingserviceapp.data.model.offerApiResponse.GetCategoriesResponse
import com.example.sellingserviceapp.data.model.offerApiResponse.GetLocationTypesResponse
import com.example.sellingserviceapp.data.model.offerApiResponse.GetPriceTypesResponse
import com.example.sellingserviceapp.data.model.offerApiResponse.GetServiceResponse
import com.example.sellingserviceapp.data.model.offerApiResponse.GetSubcategoriesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface OfferApiService {
    @GET("/api/private/category")
    @Headers("Token: true")
    suspend fun getCategories(): Response<GetCategoriesResponse>

    @GET("/api/private/subcategory/{categoryId}")
    @Headers("Token: true")
    suspend fun getSubcategories(
        @Path("categoryId") categoryId: Int,
    ): Response<GetSubcategoriesResponse>

    @GET("/api/private/price-types")
    @Headers("Token: true")
    suspend fun getPriceTypes(): Response<GetPriceTypesResponse>

    @GET("/api/private/location-types")
    @Headers("Token: true")
    suspend fun getLocationTypes(): Response<GetLocationTypesResponse>

    @GET("/api/private/offer/{id}")
    @Headers("Token: true")
    suspend fun getService(
        @Path("id") serviceId: Int
    ): Response<GetServiceResponse>

    @POST("/api/private/offer/create")
    @Headers("Token: true")
    suspend fun createServiceRequest(@Body request: CreateServiceRequest): Response<CreateServiceResponse>
}