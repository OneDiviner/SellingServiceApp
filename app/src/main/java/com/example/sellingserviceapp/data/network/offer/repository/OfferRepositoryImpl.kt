package com.example.sellingserviceapp.data.network.offer.repository

import android.util.Log
import com.example.sellingserviceapp.data.network.AuthApiError
import com.example.sellingserviceapp.data.network.authorization.response.Response
import com.example.sellingserviceapp.model.dto.ServiceDto
import com.example.sellingserviceapp.data.network.offer.response.CreateServiceResponse
import com.example.sellingserviceapp.data.network.offer.response.SearchServicesResponse
import com.example.sellingserviceapp.data.network.offer.OfferApiService
import com.example.sellingserviceapp.model.dto.CategoryDto
import com.example.sellingserviceapp.model.dto.FormatsDto
import com.example.sellingserviceapp.model.dto.NewServiceDto
import com.example.sellingserviceapp.model.dto.PriceTypeDto
import com.example.sellingserviceapp.model.dto.SubcategoryDto
import com.example.sellingserviceapp.ui.screen.createService.model.ShortService
import com.example.sellingserviceapp.ui.screen.createService.model.toRequest
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class OfferRepositoryImpl @Inject constructor(
    private val offerApiService: OfferApiService
): OfferRepository {

    override suspend fun getCategories(): Result<List<CategoryDto>> {
        return try {
            val response = offerApiService.getCategories()

            if (response.isSuccessful) {
                Result.success(response.body()!!.categories)
            } else {
                Log.d("GET_CATEGORY_REPOSITORY", "FAILURE")
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getSubcategories(categoryId: Int): Result<List<SubcategoryDto>> {
        return try {
            val response = offerApiService.getSubcategories(categoryId = categoryId)

            if (response.isSuccessful) {
                Result.success(response.body()!!.subcategories)
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getService(serviceId: Int): Result<ServiceDto> {
        return try {
            val response = offerApiService.getService(serviceId = serviceId)

            if (response.isSuccessful) {
                Log.d("GET_SERVICE_RESPONSE", response.body()!!.serviceDto.formats.toString())
                Result.success(response.body()!!.serviceDto)
            } else {
                Log.d("RESULT_FAILURE", response.message())
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPriceTypes(): Result<List<PriceTypeDto>> {
        return try {

            val response = offerApiService.getPriceTypes()

            if (response.isSuccessful) {
                Result.success(response.body()!!.priceTypes)
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getFormats(): Result<List<FormatsDto>> {
        return try {

            val response = offerApiService.getLocationTypes()

            if (response.isSuccessful) {
                Log.d("GET_FORMATS", response.body()?.formats.toString())
                Result.success(response.body()!!.formats)
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createServiceRequest(newServiceDto: NewServiceDto): Result<Int> {
        return try {
            val response = offerApiService.createServiceRequest(newServiceDto)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it.serviceId)
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

    override suspend fun updateService(service: NewServiceDto, serviceId: Int): Result<ServiceDto> {
        return try {
            val response = offerApiService.updateServiceRequest(
                serviceId = serviceId,
                request = service
            )

            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it.service)
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

    override suspend fun deleteService(serviceId: Int): Result<ResponseBody> {
        return try {
            val response = offerApiService.deleteService(serviceId = serviceId)

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
    ): Result<List<ServiceDto>> {
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
                    Log.d("OFFER_REPOSITORY", it.services.toString())
                    Result.success(it.services)
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

    override suspend fun getServiceImage(photoPath: String): Result<String> {
        return try {
            val response = offerApiService.getServiceImage(photoPath)
            if (response.isSuccessful) {
                Result.success(
                    response.body()?.byteStream()?.use { stream ->
                        val bytes = stream.readBytes()
                        android.util.Base64.encodeToString(bytes, android.util.Base64.DEFAULT)
                    } ?: throw IOException("Empty avatar response")
                )
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateServiceImage(
        serviceId: Int,
        file: MultipartBody.Part
    ): Result<ServiceDto> {
        return try {
            val response = offerApiService.updateServiceImage(serviceId, file)
            if (response.isSuccessful) {
                Result.success(response.body()!!.service)
            } else {
                Result.failure(AuthApiError.HttpError(response.code(), "Upload failed: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            when (e) {
                is IOException -> Result.failure(AuthApiError.NetworkError("Ошибка подключения"))
                is HttpException -> Result.failure(AuthApiError.HttpError(e.code(), "Ошибка: ${e.message}"))
                else -> Result.failure(AuthApiError.UnknownError("Непредвиденная ошибка: ${e.message}"))
            }
        }
    }
}

