package com.example.sellingserviceapp.data.repository

import com.example.sellingserviceapp.data.model.offerApiRequest.CreateServiceRequest
import com.example.sellingserviceapp.data.model.offerApiResponse.CreateServiceResponse
import com.example.sellingserviceapp.data.model.offerApiResponse.GetCategoriesResponse
import com.example.sellingserviceapp.data.model.offerApiResponse.GetLocationTypesResponse
import com.example.sellingserviceapp.data.model.offerApiResponse.GetPriceTypesResponse
import com.example.sellingserviceapp.data.model.offerApiResponse.GetServiceResponse
import com.example.sellingserviceapp.data.model.offerApiResponse.GetSubcategoriesResponse
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Path

interface OfferRepository {
    suspend fun getCategories(): Result<GetCategoriesResponse>
    suspend fun getSubcategories(categoryId: Int): Result<GetSubcategoriesResponse>
    suspend fun getService(serviceId: Int): Result<GetServiceResponse>
    suspend fun getPriceTypes(): Result<GetPriceTypesResponse>
    suspend fun getLocationTypes(): Result<GetLocationTypesResponse>
    suspend fun createServiceRequest(
        tittle: String,
        description: String,
        duration: Int,
        address: String,
        price: Int,
        priceTypeId: Int,
        subcategoryId: Int,
        locationTypeIds: List<Int>
    ): Result<CreateServiceResponse>
}