package com.example.sellingserviceapp.data.network.offer.repository

import com.example.sellingserviceapp.data.network.authorization.response.Response
import com.example.sellingserviceapp.model.dto.ServiceDto
import com.example.sellingserviceapp.data.network.offer.response.CreateServiceResponse
import com.example.sellingserviceapp.data.network.offer.response.SearchServicesResponse
import com.example.sellingserviceapp.model.dto.CategoryDto
import com.example.sellingserviceapp.model.dto.FormatsDto
import com.example.sellingserviceapp.model.dto.NewServiceDto
import com.example.sellingserviceapp.model.dto.PriceTypeDto
import com.example.sellingserviceapp.model.dto.SubcategoryDto
import com.example.sellingserviceapp.model.dto.UserDto
import com.example.sellingserviceapp.ui.screen.createService.model.Category
import com.example.sellingserviceapp.ui.screen.createService.model.LocationType
import com.example.sellingserviceapp.ui.screen.createService.model.PriceType
import com.example.sellingserviceapp.ui.screen.createService.model.Service
import com.example.sellingserviceapp.ui.screen.createService.model.ShortService
import com.example.sellingserviceapp.ui.screen.createService.model.Subcategory
import okhttp3.MultipartBody
import okhttp3.ResponseBody

interface OfferRepository {
    suspend fun getCategories(): Result<List<CategoryDto>>
    suspend fun getSubcategories(categoryId: Int): Result<List<SubcategoryDto>>
    suspend fun getService(serviceId: Int): Result<ServiceDto>
    suspend fun getPriceTypes(): Result<List<PriceTypeDto>>
    suspend fun getFormats(): Result<List<FormatsDto>>
    suspend fun createServiceRequest(newServiceDto: NewServiceDto): Result<Int>
    suspend fun updateService(service: NewServiceDto, serviceId: Int): Result<ServiceDto>
    suspend fun deleteService(serviceId: Int): Result<ResponseBody>

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
    ): Result<List<ServiceDto>>

    suspend fun getServiceImage(photoPath: String): Result<String>

    suspend fun updateServiceImage(serviceId: Int, file: MultipartBody.Part): Result<ServiceDto>
}