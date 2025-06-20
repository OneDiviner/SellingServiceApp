package com.example.sellingserviceapp.data.manager

import android.util.Log
import com.example.sellingserviceapp.data.manager.categoryData.ICategoryDataRepository
import com.example.sellingserviceapp.data.manager.formatData.IFormatDataRepository
import com.example.sellingserviceapp.data.manager.priceTypeData.IPriceTypeDataRepository
import com.example.sellingserviceapp.data.manager.serviceData.IServiceDataRepository
import com.example.sellingserviceapp.data.manager.userServiceData.IUserServiceDataRepository
import com.example.sellingserviceapp.data.manager.subcategoryData.ISubcategoryDataRepository
import com.example.sellingserviceapp.data.network.authorization.repository.AuthRepository
import com.example.sellingserviceapp.data.network.booking.Booking
import com.example.sellingserviceapp.data.network.booking.IBookingRepository
import com.example.sellingserviceapp.data.network.booking.Status
import com.example.sellingserviceapp.data.network.offer.repository.OfferRepository
import com.example.sellingserviceapp.data.manager.userData.IUserDataRepository
import com.example.sellingserviceapp.model.domain.BookingWithData
import com.example.sellingserviceapp.model.domain.ServiceDomain
import com.example.sellingserviceapp.model.mapper.ServiceConverters.toDomain
import com.example.sellingserviceapp.model.mapper.serviceDtoListToDomainList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

//TODO: Если null не делать запрос

open class DataManager @Inject constructor(
    private val userDataRepository: IUserDataRepository,
    private val userServiceDataRepository: IUserServiceDataRepository,
    private val serviceDataRepository: IServiceDataRepository,
    private val categoryDataRepository: ICategoryDataRepository,
    private val subcategoryDataRepository: ISubcategoryDataRepository,
    private val formatDataRepository: IFormatDataRepository,
    private val priceTypeDataRepository: IPriceTypeDataRepository,
    private val authRepository: AuthRepository,
    private val offerRepository: OfferRepository,
    private val bookingRepository: IBookingRepository,
) : IUserDataRepository by userDataRepository,
    IUserServiceDataRepository by userServiceDataRepository,
    IServiceDataRepository by serviceDataRepository,
    ICategoryDataRepository by categoryDataRepository,
    ISubcategoryDataRepository by subcategoryDataRepository,
    IFormatDataRepository by formatDataRepository,
    IPriceTypeDataRepository by priceTypeDataRepository
{

    suspend fun requestServices(
        page: Int,
        size: Int,
        categoryId: Int? = null,
        title: String? = null
    ): List<ServiceDomain> = coroutineScope { // Используем coroutineScope для структурированного параллелизма
        val requestServicesDtoResult = offerRepository.searchServices(page = page, size = size, title = title, categoryId = categoryId?.toLong())
        requestServicesDtoResult.fold(
            onSuccess = { servicesDto ->
                if (servicesDto.services.isNullOrEmpty()) {
                    return@fold emptyList<ServiceDomain>()
                }

                val imageDeferreds = servicesDto.services.map { serviceDto ->
                    async(Dispatchers.IO) { // async запускает корутину и возвращает Deferred
                        // Убедитесь, что photoPath это правильный путь
                        getServiceImage(serviceDto.photoPath ?: serviceDto.id.toString()) // Используем photoPath если есть, иначе id
                    }
                }
                val images = imageDeferreds.awaitAll() // awaitAll дожидается выполнения всех Deferred

                val servicesWithImages = servicesDto.services.mapIndexed { index, serviceDto ->
                    serviceDto.toDomain(images[index])
                }

                servicesWithImages
            },
            onFailure = { exception ->
                Log.e("DataManager", "Failed to request services: ${exception.message}")
                emptyList()
            }
        )
    }

    open suspend fun getBookingAsExecutor(
        page: Int = 0,
        size: Int = 20,
        statusId: Int? = null
    ): List<BookingWithData> {
        val bookingsResponse = bookingRepository.getBookingAsExecutor(page, size, statusId)
        bookingsResponse.onSuccess {
            return bookingWithData(it.listOfBooking)
        }
        return emptyList()
    }

    open suspend fun getBookingAsExecutor(
        date: String
    ): List<BookingWithData> {
        val bookingsResponse = bookingRepository.getBookingAsExecutor(date)
        bookingsResponse.onSuccess {
            return bookingWithData(it.listOfBooking)
        }
        return emptyList()
    }

    open suspend fun getBookingAsClient(
        page: Int = 0,
        size: Int = 20,
        statusId: Int? = null
    ): List<BookingWithData> {
        val bookingsResponse = bookingRepository.getBookingAsClient(page, size, statusId)
        bookingsResponse.onSuccess {
            return bookingWithData(it.listOfBooking)
        }
        return emptyList()
    }

    open suspend fun getBookingAsClient(
        date: String
    ): List<BookingWithData> {
        val bookingsResponse = bookingRepository.getBookingAsClient(date)
        bookingsResponse.onSuccess {
            return bookingWithData(it.listOfBooking)
        }
        return emptyList()
    }

    open suspend fun bookingWithData(listOfBooking: List<Booking>): List<BookingWithData> {
        if(listOfBooking.isNotEmpty()) {
            val userIds = listOfBooking.map { it.userId }
            val serviceIds = listOfBooking.map { it.offerId }
            val usersList = authRepository.getUsersListById(userIds).getOrElse { emptyList() }
            val serviceDomainList = serviceDtoListToDomainList(offerRepository.getServicesList(serviceIds).getOrElse { emptyList() })
            return listOfBooking.map { booking ->
                val user = usersList.find { it.id == booking.userId }
                val service = serviceDomainList.find { it.id == booking.offerId }
                BookingWithData(
                    booking = booking,
                    user = user,
                    service = service
                )
            }
        }
       return emptyList()
    }

    open suspend fun confirmBookingAsExecutor(bookingId: Int) {
        val response = bookingRepository.confirmBookingAsExecutor(bookingId)
        response.onSuccess {

        }
    }

    open suspend fun rejectBookingAsExecutor(bookingId: Int) {
        val response = bookingRepository.rejectBookingAsExecutor(bookingId)
        response.onSuccess {

        }
    }

    open suspend fun getBookingStatuses(): List<Status> {
        val statusesResponse = bookingRepository.getBookingStatuses()
        statusesResponse.onSuccess {
            return it.statuses
        }
        return emptyList()
    }

}