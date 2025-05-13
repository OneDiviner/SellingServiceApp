package com.example.sellingserviceapp.data.network.offer

import com.example.sellingserviceapp.data.network.offer.request.CreateServiceRequest
import com.example.sellingserviceapp.data.network.offer.response.CreateServiceResponse
import com.example.sellingserviceapp.data.network.offer.response.GetCategoriesResponse
import com.example.sellingserviceapp.data.network.offer.response.GetLocationTypesResponse
import com.example.sellingserviceapp.data.network.offer.response.GetPriceTypesResponse
import com.example.sellingserviceapp.data.network.offer.response.GetServiceResponse
import com.example.sellingserviceapp.data.network.offer.response.GetSubcategoriesResponse
import com.example.sellingserviceapp.data.network.offer.response.SearchServicesResponse
import com.example.sellingserviceapp.data.network.offer.response.SearchUserServiceResponse
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

    @GET("/api/private/offer/search")
    @Headers("Token: true")
    suspend fun searchServices(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("title") title: String? = null,
        @Query("durationMin") durationMin: Long? = null,
        @Query("durationMax") durationMax: Long? = null,
        @Query("priceMin") priceMin: Long? = null,
        @Query("priceMax") priceMax: Long? = null,
        @Query("locationTypesIds") locationTypesIds: List<Int>? = null,
        @Query("priceTypeId") priceTypeId: Long? = null,
        @Query("categoryId") categoryId: Long? = null,
        @Query("subcategoryId") subcategoryId: Long? = null
    ): Response<SearchServicesResponse>

    @GET("/api/private/offer/search/my")
    @Headers("Token: true")
    suspend fun searchUserServices(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("title") title: String? = null,
        @Query("durationMin") durationMin: Long? = null,
        @Query("durationMax") durationMax: Long? = null,
        @Query("priceMin") priceMin: Long? = null,
        @Query("priceMax") priceMax: Long? = null,
        @Query("locationTypesIds") locationTypesIds: List<Int>? = null,
        @Query("priceTypeId") priceTypeId: Long? = null,
        @Query("categoryId") categoryId: Long? = null,
        @Query("subcategoryId") subcategoryId: Long? = null
    ): Response<SearchUserServiceResponse>
}