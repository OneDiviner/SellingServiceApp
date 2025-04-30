package com.example.sellingserviceapp.data.repository

import com.example.sellingserviceapp.data.model.AuthApiError
import com.example.sellingserviceapp.data.model.authApiRequest.SecondStepRegisterRequest
import com.example.sellingserviceapp.data.model.offerApiRequest.CreateServiceRequest
import com.example.sellingserviceapp.data.model.offerApiResponse.CreateServiceResponse
import com.example.sellingserviceapp.data.model.offerApiResponse.GetCategoriesResponse
import com.example.sellingserviceapp.data.model.offerApiResponse.GetLocationTypesResponse
import com.example.sellingserviceapp.data.model.offerApiResponse.GetPriceTypesResponse
import com.example.sellingserviceapp.data.model.offerApiResponse.GetServiceResponse
import com.example.sellingserviceapp.data.model.offerApiResponse.GetSubcategoriesResponse
import com.example.sellingserviceapp.data.network.OfferApiService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class OfferRepositoryImpl @Inject constructor(
    private val offerApiService: OfferApiService
): OfferRepository {
    override suspend fun getCategories(): Result<GetCategoriesResponse> {
        return try {

            val response = offerApiService.getCategories()

            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getSubcategories(categoryId: Int): Result<GetSubcategoriesResponse> {
        return try {
            val response = offerApiService.getSubcategories(categoryId = categoryId)

            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getService(serviceId: Int): Result<GetServiceResponse> {
        return try {
            val response = offerApiService.getService(serviceId = serviceId)

            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPriceTypes(): Result<GetPriceTypesResponse> {
        return try {

            val response = offerApiService.getPriceTypes()

            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getLocationTypes(): Result<GetLocationTypesResponse> {
        return try {

            val response = offerApiService.getLocationTypes()

            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createServiceRequest(
        tittle: String,
        description: String,
        duration: Int,
        address: String,
        price: Int,
        priceTypeId: Int,
        subcategoryId: Int,
        locationTypeIds: List<Int>
    ): Result<CreateServiceResponse> {
        return try {
            val response = offerApiService.createServiceRequest(
                CreateServiceRequest(
                    tittle = tittle,
                    description = description,
                    duration = duration,
                    address = address,
                    price = price,
                    priceTypeId = priceTypeId,
                    subcategoryId = subcategoryId,
                    locationTypeIds = locationTypeIds
                )
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