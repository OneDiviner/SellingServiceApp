package com.example.sellingserviceapp.data.network.offer

import com.example.sellingserviceapp.data.network.authorization.response.UpdateAvatarResponse
import com.example.sellingserviceapp.data.network.offer.request.CreateServiceRequest
import com.example.sellingserviceapp.data.network.offer.response.CreateServiceResponse
import com.example.sellingserviceapp.data.network.offer.response.GetCategoriesResponse
import com.example.sellingserviceapp.data.network.offer.response.GetFormatsResponse
import com.example.sellingserviceapp.data.network.offer.response.GetPriceTypesResponse
import com.example.sellingserviceapp.data.network.offer.response.GetServiceResponse
import com.example.sellingserviceapp.data.network.offer.response.GetSubcategoriesResponse
import com.example.sellingserviceapp.data.network.offer.response.SearchServicesResponse
import com.example.sellingserviceapp.data.network.offer.response.SearchUserServiceResponse
import com.example.sellingserviceapp.data.network.offer.response.UpdateServiceImageResponse
import com.example.sellingserviceapp.data.network.offer.response.UpdateServiceResponse
import com.example.sellingserviceapp.model.dto.NewServiceDto
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
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
    suspend fun getLocationTypes(): Response<GetFormatsResponse>

    @GET("/api/private/offer/{id}")
    @Headers("Token: true")
    suspend fun getService(
        @Path("id") serviceId: Int
    ): Response<GetServiceResponse>

    @POST("/api/private/offer/create")
    @Headers("Token: true")
    suspend fun createServiceRequest(@Body request: NewServiceDto): Response<CreateServiceResponse>

    @PATCH("/api/private/offer/update/{id}")
    @Headers("Token: true")
    suspend fun updateServiceRequest(
        @Path("id") serviceId: Int,
        @Body request: NewServiceDto
    ): Response<UpdateServiceResponse>

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

    @GET("/api/private/offer/photo")
    @Headers("Token: true")
    suspend fun getServiceImage(@Query("photoPath") photoPath: String): Response<ResponseBody>

    @Multipart
    @PATCH("/api/private/offer/update-photo/{id}")
    @Headers("Token: true")
    suspend fun updateServiceImage(
        @Path("id") serviceId: Int,
        @Part file: MultipartBody.Part
    ): Response<UpdateServiceImageResponse>

    @DELETE("/api/private/offer/delete/{id}")
    @Headers("Token: true")
    suspend fun deleteService(@Path("id") serviceId: Int): Response<ResponseBody>
}