package com.example.sellingserviceapp.data.network.offer.repository

import android.util.Log
import com.example.sellingserviceapp.data.network.AuthApiError
import com.example.sellingserviceapp.data.network.Mapper
import com.example.sellingserviceapp.data.network.offer.response.CreateServiceResponse
import com.example.sellingserviceapp.data.network.offer.response.SearchServicesResponse
import com.example.sellingserviceapp.data.network.offer.response.SearchUserServiceResponse
import com.example.sellingserviceapp.data.network.offer.OfferApiService
import com.example.sellingserviceapp.ui.screen.createService.model.Address
import com.example.sellingserviceapp.ui.screen.createService.model.Category
import com.example.sellingserviceapp.ui.screen.createService.model.LocationType
import com.example.sellingserviceapp.ui.screen.createService.model.PriceType
import com.example.sellingserviceapp.ui.screen.createService.model.Service
import com.example.sellingserviceapp.ui.screen.createService.model.ShortService
import com.example.sellingserviceapp.ui.screen.createService.model.Subcategory
import com.example.sellingserviceapp.ui.screen.createService.model.toRequest
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class OfferRepositoryImpl @Inject constructor(
    private val offerApiService: OfferApiService
): OfferRepository {

    private val mapper = Mapper()

    override suspend fun getCategories(): Result<List<Category>> {
        return try {
            val response = offerApiService.getCategories()

            if (response.isSuccessful) {
                Result.success(mapper.map(response.body()!!))
            } else {
                Log.d("GET_CATEGORY_REPOSITORY", "FAILURE")
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getSubcategories(categoryId: Int): Result<List<Subcategory>> {
        return try {
            val response = offerApiService.getSubcategories(categoryId = categoryId)

            if (response.isSuccessful) {
                Result.success(mapper.map(response.body()!!))
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getService(serviceId: Int): Result<Service> {
        return try {
            val response = offerApiService.getService(serviceId = serviceId)

            if (response.isSuccessful) {
                Result.success(
                    Service(
                        id = response.body()!!.service.id,
                        userId = response.body()!!.service.user_id,
                        tittle = response.body()!!.service.tittle,
                        description = response.body()!!.service.description,
                        duration = response.body()!!.service.duration.toString(),
                        photoPath = response.body()!!.service.photoPath,
                        price = response.body()!!.service.price.toString(),
                        createdAt = response.body()!!.service.createdAt,
                        updatedAt = response.body()!!.service.updatedAt,
                        locationTypes = response.body()!!.service.locationTypes.map {
                            Address(
                                name = mapper.locationMap(it.name),
                                location = it.location
                            )
                        },
                        priceType = mapper.priceMap(response.body()!!.service.priceType),
                        status = mapper.statusMap(response.body()!!.service.status),
                        category = mapper.categoryMap(response.body()!!.service.category),
                        subcategory = mapper.subcategoryMap(response.body()!!.service.subcategory)

                    )
                )
            } else {
                Log.d("RESULT_FAILURE", response.message())
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPriceTypes(): Result<List<PriceType>> {
        return try {

            val response = offerApiService.getPriceTypes()

            if (response.isSuccessful) {
                Log.d("GET_PRICE_TYPE_REPOSITORY", response.body()!!.priceTypes.toString())
                Result.success(mapper.map(response.body()!!))
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getLocationTypes(): Result<List<LocationType>> {
        return try {

            val response = offerApiService.getLocationTypes()

            if (response.isSuccessful) {
                Result.success(mapper.map(response.body()!!))
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createServiceRequest(shortService: ShortService): Result<CreateServiceResponse> {
        return try {
            val response = offerApiService.createServiceRequest(shortService.toRequest())

            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(AuthApiError.EmptyBody())
            } else {
                //Добавить обработчик кодов ошибки
                Result.failure(AuthApiError.HttpError(response.code(), "Ошибка: ${response.code()}"))
            }
        } catch (e: Exception) {
            when (e) {
                is IOException -> Result.failure(AuthApiError.NetworkError("Нет интернет соединения"))
                is HttpException -> Result.failure(AuthApiError.HttpError(e.code(), "Ошибка: ${e.message}"))
                else -> Result.failure(AuthApiError.UnknownError("Непредвиденная ошибка: ${e.message}"))
            }
        }
    }

    override suspend fun searchServices(
        page: Int,
        size: Int,
        title: String?,
        durationMin: Long?,
        durationMax: Long?,
        priceMin: Long?,
        priceMax: Long?,
        locationTypesIds: List<Int>?,
        priceTypeId: Long?,
        categoryId: Long?,
        subcategoryId: Long?
    ): Result<SearchServicesResponse> {
        return try {
            val response = offerApiService.searchServices(
                page = page,
                size = size,
                title = title,
                durationMin = durationMin,
                durationMax = durationMax,
                priceMin = priceMin,
                priceMax = priceMax,
                locationTypesIds = locationTypesIds,
                priceTypeId = priceTypeId,
                categoryId = categoryId,
                subcategoryId = subcategoryId
            )

            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(AuthApiError.EmptyBody())
            } else {
                //Добавить обработчик кодов ошибки
                Result.failure(AuthApiError.HttpError(response.code(), "Ошибка: ${response.code()}"))
            }
        } catch (e: Exception) {
            when (e) {
                is IOException -> Result.failure(AuthApiError.NetworkError("Нет интернет соединения"))
                is HttpException -> Result.failure(AuthApiError.HttpError(e.code(), "Ошибка: ${e.message}"))
                else -> Result.failure(AuthApiError.UnknownError("Непредвиденная ошибка: ${e.message}"))
            }
        }
    }

    override suspend fun searchUserServices(
        page: Int,
        size: Int,
        title: String?,
        durationMin: Long?,
        durationMax: Long?,
        priceMin: Long?,
        priceMax: Long?,
        locationTypesIds: List<Int>?,
        priceTypeId: Long?,
        categoryId: Long?,
        subcategoryId: Long?
    ): Result<SearchUserServiceResponse> {
        return try {
            val response = offerApiService.searchUserServices(
                page = page,
                size = size,
                title = title,
                durationMin = durationMin,
                durationMax = durationMax,
                priceMin = priceMin,
                priceMax = priceMax,
                locationTypesIds = locationTypesIds,
                priceTypeId = priceTypeId,
                categoryId = categoryId,
                subcategoryId = subcategoryId
            )


            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(AuthApiError.EmptyBody())
            } else {
                //Добавить обработчик кодов ошибки
                Result.failure(AuthApiError.HttpError(response.code(), "Ошибка: ${response.code()}"))
            }
        } catch (e: Exception) {
            when (e) {
                is IOException -> Result.failure(AuthApiError.NetworkError("Нет интернет соединения"))
                is HttpException -> Result.failure(AuthApiError.HttpError(e.code(), "Ошибка: ${e.message}"))
                else -> Result.failure(AuthApiError.UnknownError("Непредвиденная ошибка: ${e.message}"))
            }
        }
    }
}

