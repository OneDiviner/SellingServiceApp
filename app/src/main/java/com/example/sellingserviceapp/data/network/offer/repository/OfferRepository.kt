package com.example.sellingserviceapp.data.network.offer.repository

import com.example.sellingserviceapp.data.network.offer.response.CreateServiceResponse
import com.example.sellingserviceapp.data.network.offer.response.SearchServicesResponse
import com.example.sellingserviceapp.data.network.offer.response.SearchUserServiceResponse
import com.example.sellingserviceapp.ui.screen.createService.model.Category
import com.example.sellingserviceapp.ui.screen.createService.model.LocationType
import com.example.sellingserviceapp.ui.screen.createService.model.PriceType
import com.example.sellingserviceapp.ui.screen.createService.model.Service
import com.example.sellingserviceapp.ui.screen.createService.model.ShortService
import com.example.sellingserviceapp.ui.screen.createService.model.Subcategory

interface OfferRepository {
    suspend fun getCategories(): Result<List<Category>>
    suspend fun getSubcategories(categoryId: Int): Result<List<Subcategory>>
    suspend fun getService(serviceId: Int): Result<Service>
    suspend fun getPriceTypes(): Result<List<PriceType>>
    suspend fun getLocationTypes(): Result<List<LocationType>>
    suspend fun createServiceRequest(shortService: ShortService): Result<CreateServiceResponse>

    suspend fun searchServices(
       page: Int,
       size: Int,
       title: String? = null,
       durationMin: Long? = null,
       durationMax: Long? = null,
        priceMin: Long? = null,
        priceMax: Long? = null,
        locationTypesIds: List<Int>? = null,
        priceTypeId: Long? = null,
        categoryId: Long? = null,
        subcategoryId: Long? = null
    ): Result<SearchServicesResponse>

    suspend fun searchUserServices(
        page: Int,
        size: Int,
        title: String? = null,
        durationMin: Long? = null,
        durationMax: Long? = null,
        priceMin: Long? = null,
        priceMax: Long? = null,
        locationTypesIds: List<Int>? = null,
        priceTypeId: Long? = null,
        categoryId: Long? = null,
        subcategoryId: Long? = null
    ): Result<SearchUserServiceResponse>
}