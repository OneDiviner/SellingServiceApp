package com.example.sellingserviceapp.data.manager

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.ui.geometry.isEmpty
import com.example.sellingserviceapp.data.manager.categoryData.ICategoryDataRepository
import com.example.sellingserviceapp.data.manager.feedbackData.FeedbackDataRepository
import com.example.sellingserviceapp.data.manager.feedbackData.IFeedbackDataRepository
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
import com.example.sellingserviceapp.data.network.authorization.response.Response
import com.example.sellingserviceapp.data.network.feedback.model.FeedbackDto
import com.example.sellingserviceapp.data.network.offer.response.Pageable
import com.example.sellingserviceapp.data.network.offer.response.SearchServicesResponse
import com.example.sellingserviceapp.model.FeedbackWithData
import com.example.sellingserviceapp.model.SearchServicesPagination
import com.example.sellingserviceapp.model.ServiceWithRating
import com.example.sellingserviceapp.model.domain.BookingWithData
import com.example.sellingserviceapp.model.domain.ServiceDomain
import com.example.sellingserviceapp.model.domain.UserDomain
import com.example.sellingserviceapp.model.dto.UserDto
import com.example.sellingserviceapp.model.mapper.ServiceConverters.toDomain
import com.example.sellingserviceapp.model.mapper.UserConverters.toDomain
import com.example.sellingserviceapp.model.mapper.mapBookingListAsClient
import com.example.sellingserviceapp.model.mapper.mapBookingListAsExecutor
import com.example.sellingserviceapp.model.mapper.mapStatusListAsExecutor
import com.example.sellingserviceapp.model.mapper.serviceDtoListToDomainList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import okhttp3.internal.checkOffsetAndCount
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
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
    private val feedbackDataRepository: IFeedbackDataRepository,
    private val authRepository: AuthRepository,
    private val offerRepository: OfferRepository,
    private val bookingRepository: IBookingRepository,
) : IUserDataRepository by userDataRepository,
    IUserServiceDataRepository by userServiceDataRepository,
    IServiceDataRepository by serviceDataRepository,
    ICategoryDataRepository by categoryDataRepository,
    ISubcategoryDataRepository by subcategoryDataRepository,
    IFormatDataRepository by formatDataRepository,
    IPriceTypeDataRepository by priceTypeDataRepository,
    IFeedbackDataRepository by feedbackDataRepository
{
    suspend fun requestServices(
        page: Int,
        size: Int,
        categoryId: Int? = null,
        title: String? = null
    ): SearchServicesPagination = coroutineScope {
        val requestServicesDtoResult = offerRepository.searchServices(
            page = page,
            size = size,
            title = title,
            categoryId = categoryId?.toLong()
        )

        requestServicesDtoResult.fold(
            onSuccess = { servicesResponseDto ->
                if (servicesResponseDto.services.isNullOrEmpty()) {

                    return@fold SearchServicesPagination(
                        services = emptyList(),
                        response = servicesResponseDto.response,
                        pageable = servicesResponseDto.pageable,
                    )
                }

                val imageDeferreds = servicesResponseDto.services.map { serviceDto ->
                    async(Dispatchers.IO) {
                        getServiceImage(serviceDto.photoPath ?: serviceDto.id.toString())
                    }
                }
                val images = imageDeferreds.awaitAll()

                val servicesWithImages = servicesResponseDto.services.mapIndexed { index, serviceDto ->
                    serviceDto.toDomain(images[index])
                }

                SearchServicesPagination(
                    services = servicesWithImages,
                    response = servicesResponseDto.response,
                    pageable = servicesResponseDto.pageable,
                )
            },
            onFailure = { exception ->
                Log.e("DataManager", "Failed to request services: ${exception.message}")
                SearchServicesPagination(services = emptyList(), response = Response(message = "", isSuccess = false), pageable = Pageable(page = 0, pageMax = 0, size = ""))
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
            return bookingAsExecutorWithData(mapBookingListAsExecutor(it.listOfBooking))
        }
        return emptyList()
    }
    open suspend fun getBookingAsExecutor(
        date: String
    ): List<BookingWithData> {
        val bookingsResponse = bookingRepository.getBookingAsExecutor(date)
        bookingsResponse.onSuccess {
            return bookingAsExecutorWithData(mapBookingListAsExecutor(it.listOfBooking))
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
            return bookingAsClientWithData(mapBookingListAsClient(it.listOfBooking))
        }
        return emptyList()
    }
    open suspend fun getBookingAsClient(
        date: String
    ): List<BookingWithData> {
        val bookingsResponse = bookingRepository.getBookingAsClient(date)
        bookingsResponse.onSuccess {
            return bookingAsClientWithData(mapBookingListAsClient(it.listOfBooking))
        }
        return emptyList()
    }
    open suspend fun bookingAsClientWithData(listOfBooking: List<Booking>): List<BookingWithData> {
        if (listOfBooking.isNotEmpty()) {
            val serviceIds = listOfBooking.map { it.offerId }

            val serviceDtoList = offerRepository.getServicesList(serviceIds).getOrElse { emptyList() }
            if (serviceDtoList.isEmpty()) { // Если сервисов нет, то и данных для букинга не будет
                return emptyList()
            }

            val servicesWithPhotosDeferred = serviceDtoList.map { serviceDto ->
                coroutineScope {
                    async(Dispatchers.IO) { // Запускаем загрузку изображения в фоновом потоке
                        val image = getServiceImage(serviceDto.photoPath ?: serviceDto.id.toString())
                        serviceDto.toDomain(image) // Предполагаем, что toDomain принимает ServiceDto и String? (фото)
                    }
                }
            }
            val serviceDomainListWithPhotos = servicesWithPhotosDeferred.awaitAll()
            val userIds = serviceDomainListWithPhotos.mapNotNull { it.userId } // mapNotNull, если userId может быть null
            val usersList = if (userIds.isNotEmpty()) {
                authRepository.getUsersListById(userIds).getOrElse { emptyList() }
            } else {
                emptyList()
            }
            return listOfBooking.map { booking ->
                val service = serviceDomainListWithPhotos.find { it.id == booking.offerId }
                val user = if (service?.userId != null) {
                    usersList.find { u -> u.id == service.userId }
                } else {
                    null
                }
                BookingWithData(
                    booking = booking,
                    user = user,
                    service = service
                )
            }
        }
        return emptyList()
    }
    open suspend fun bookingAsExecutorWithData(listOfBooking: List<Booking>): List<BookingWithData> {
        if (listOfBooking.isNotEmpty()) {
            val userIds = listOfBooking.map { it.userId }
            val userList = userDataRepository.fetchUserList(userIds)
            val serviceIds = listOfBooking.map { it.offerId }
            val serviceDtoList = offerRepository.getServicesList(serviceIds).getOrElse { emptyList() } // ЗАМЕНИТЕ НА ВАШ РЕАЛЬНЫЙ МЕТОД
            val serviceDomainListWithPhotosDeferred = serviceDtoList.map { serviceDto ->
                coroutineScope {
                    async(Dispatchers.IO) {
                        val image = getServiceImage(serviceDto.photoPath ?: serviceDto.id.toString())
                        serviceDto.toDomain(image) // Преобразуем ServiceDto в ServiceDomain с фото
                    }
                }
            }
            val serviceDomainListWithPhotos = serviceDomainListWithPhotosDeferred.awaitAll()
            return listOfBooking.map { booking ->
                val user = userList.find { it.id == booking.userId }
                val service = serviceDomainListWithPhotos.find { it.id == booking.offerId }
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDateTimeString(inputDateTime: String?): String {
        if (inputDateTime.isNullOrBlank()) {
            return "Неверная дата"
        }

        return try {

            val inputFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

            val localDateTime = LocalDateTime.parse(inputDateTime, inputFormatter)

            val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")

            localDateTime.format(outputFormatter)
        } catch (e: DateTimeParseException) {
            e.printStackTrace()
            "Неверный формат даты"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getFeedbackWithDataForService(
        serviceId: Int,
        page: Int,
        size: Int
    ) : List<FeedbackWithData> {
        val feedbackList = feedbackDataRepository.getFeedbackForService(serviceId = serviceId, page = page, size = size)
        Log.d("FEEDBACK", feedbackList.toString())
        if (feedbackList.isNotEmpty() == true) {
            val userIds = feedbackList.map { it.userId }
            val userList = userDataRepository.fetchUserList(userIds)

            val serviceIds = feedbackList.map { it.offerId }
            val serviceList = serviceDataRepository.fetchServiceList(serviceIds)

            val userWithPhotoListDeferred = userList.map { user ->
                coroutineScope {
                    async(Dispatchers.IO) {
                        val image = fetchUserAvatar(user.avatarPath ?: "")
                        user.toDomain(image)
                    }
                }
            }

            val usersWithPhotoList = userWithPhotoListDeferred.awaitAll()

            return feedbackList.map { feedback ->
                val user = usersWithPhotoList.find { user -> user.id == feedback.userId }
                val service = serviceList.find { service -> service.id == feedback.offerId }
                FeedbackWithData(
                    feedback = feedback.copy(createdAt = formatDateTimeString(feedback.createdAt)),
                    user = user ?: UserDomain.EMPTY,
                    service = service ?: ServiceDomain.EMPTY
                )
            }

        }
        return emptyList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getUserFeedbackWithData(
        page: Int,
        size: Int
    ) : List<FeedbackWithData> {
        val feedbackList = feedbackDataRepository.getUserFeedback(page = page, size = size)
        Log.d("FEEDBACK", feedbackList.toString())
        if (feedbackList.isNotEmpty() == true) {
            val userIds = feedbackList.map { it.userId }
            val userList = userDataRepository.fetchUserList(userIds)

            val serviceIds = feedbackList.map { it.offerId }
            val serviceList = serviceDataRepository.fetchServiceList(serviceIds)

            val userWithPhotoListDeferred = userList.map { user ->
                coroutineScope {
                    async(Dispatchers.IO) {
                        val image = fetchUserAvatar(user.avatarPath ?: "")
                        user.toDomain(image)
                    }
                }
            }

            val usersWithPhotoList = userWithPhotoListDeferred.awaitAll()

            val serviceWithPhotoListDeferred = serviceList.map { service ->
                coroutineScope {
                    async(Dispatchers.IO) {
                        val image = getServiceImage(service.photoPath ?: "")
                        service.copy(photo = image)
                    }
                }
            }

            val serviceWithPhotoList = serviceWithPhotoListDeferred.awaitAll()

            return feedbackList.map { feedback ->
                val user = usersWithPhotoList.find { user -> user.id == feedback.userId }
                val service = serviceWithPhotoList.find { service -> service.id == feedback.offerId }
                FeedbackWithData(
                    feedback = feedback.copy(createdAt = formatDateTimeString(feedback.createdAt)),
                    user = user ?: UserDomain.EMPTY,
                    service = service ?: ServiceDomain.EMPTY
                )
            }

        }
        return emptyList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getAvailableFeedbackWithData(
        page: Int,
        size: Int
    ) : List<FeedbackWithData> {
        val feedbackList = feedbackDataRepository.getAvailableFeedback(page = page, size = size)
        Log.d("FEEDBACK", feedbackList.toString())
        if (feedbackList.isNotEmpty() == true) {

            val serviceIds = feedbackList.map { it.serviceId }
            val serviceList = serviceDataRepository.fetchServiceList(serviceIds)

            val serviceWithPhotoListDeferred = serviceList.map { service ->
                coroutineScope {
                    async(Dispatchers.IO) {
                        val image = getServiceImage(service.photoPath ?: "")
                        service.copy(photo = image)
                    }
                }
            }

            val serviceWithPhotoList = serviceWithPhotoListDeferred.awaitAll()

            return feedbackList.map { feedback ->
                val service = serviceWithPhotoList.find { service -> service.id == feedback.serviceId }
                FeedbackWithData(
                    feedback = FeedbackDto.EMPTY.copy(bookingId = feedback.bookingId),
                    user = UserDomain.EMPTY,
                    service = service ?: ServiceDomain.EMPTY
                )
            }

        }
        return emptyList()
    }

}