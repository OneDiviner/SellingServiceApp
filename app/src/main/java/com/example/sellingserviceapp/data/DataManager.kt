package com.example.sellingserviceapp.data

import android.util.Base64
import android.util.Log
import com.example.sellingserviceapp.data.di.GlobalAppState
import com.example.sellingserviceapp.data.di.SecureTokenStorage
import com.example.sellingserviceapp.data.local.AppDataBase
import com.example.sellingserviceapp.data.local.repository.FormatsRepository
import com.example.sellingserviceapp.data.local.repository.IPriceTypesRepository
import com.example.sellingserviceapp.data.local.repository.ISubcategoriesRepository
import com.example.sellingserviceapp.data.local.repository.PriceTypesRepository
import com.example.sellingserviceapp.data.local.repository.SubcategoriesRepository
import com.example.sellingserviceapp.model.entity.CategoryEntity
import com.example.sellingserviceapp.model.entity.SubcategoryEntity
import com.example.sellingserviceapp.data.local.repository.categories.ICategoriesRepository
import com.example.sellingserviceapp.data.local.repository.service.IServiceRepository
import com.example.sellingserviceapp.data.local.repository.user.IUserRepository
import com.example.sellingserviceapp.data.network.authorization.repository.AuthRepository
import com.example.sellingserviceapp.model.mapper.UserConverters.toDomain
import com.example.sellingserviceapp.model.mapper.UserConverters.toDto
import com.example.sellingserviceapp.model.mapper.UserConverters.toEntity
import com.example.sellingserviceapp.data.network.offer.repository.OfferRepository
import com.example.sellingserviceapp.model.domain.CategoryDomain
import com.example.sellingserviceapp.model.domain.FormatsDomain
import com.example.sellingserviceapp.model.domain.NewServiceDomain
import com.example.sellingserviceapp.model.domain.PriceTypeDomain
import com.example.sellingserviceapp.model.domain.ServiceDomain
import com.example.sellingserviceapp.model.domain.SubcategoryDomain
import com.example.sellingserviceapp.ui.screen.createService.model.Category
import com.example.sellingserviceapp.ui.screen.createService.model.Subcategory
import com.example.sellingserviceapp.model.domain.UserDomain
import com.example.sellingserviceapp.model.dto.CategoryDto
import com.example.sellingserviceapp.model.dto.FormatsDto
import com.example.sellingserviceapp.model.dto.PriceTypeDto
import com.example.sellingserviceapp.model.dto.ServiceDto
import com.example.sellingserviceapp.model.dto.SubcategoryDto
import com.example.sellingserviceapp.model.entity.ServiceEntity
import com.example.sellingserviceapp.model.entity.UserEntity
import com.example.sellingserviceapp.model.mapper.NewServiceMapper.toDto
import com.example.sellingserviceapp.model.mapper.ServiceConverters.toDomain
import com.example.sellingserviceapp.model.mapper.ServiceConverters.toEntity
import com.example.sellingserviceapp.model.mapper.categoriesDtoListToEntityList
import com.example.sellingserviceapp.model.mapper.categoriesEntityListToDomainList
import com.example.sellingserviceapp.model.mapper.formatsDtoListToEntityList
import com.example.sellingserviceapp.model.mapper.formatsEntityListToDomainList
import com.example.sellingserviceapp.model.mapper.priceTypesDtoListToEntityList
import com.example.sellingserviceapp.model.mapper.priceTypesEntityListToDomainList
import com.example.sellingserviceapp.model.mapper.serviceDtoListToDomainList
import com.example.sellingserviceapp.model.mapper.serviceEntityFlowToDomainFlow
import com.example.sellingserviceapp.model.mapper.serviceEntityListToDomainList
import com.example.sellingserviceapp.model.mapper.subcategoriesDtoListToEntityList
import com.example.sellingserviceapp.model.mapper.subcategoriesEntityListToDomainList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

interface User {
    suspend fun fetchUser(): UserEntity
    suspend fun fetchUserAvatar(userId: Int): String?
    suspend fun insertUser()
    suspend fun getUserFlow(): Flow<UserDomain>

    suspend fun getUser(): Flow<UserDomain>
    suspend fun updateUser(userDomain: UserDomain)
    suspend fun getAvatar(avatarPath: String): String?
    suspend fun updateAvatar(base64: String)
    suspend fun getUserById(userId: Int): UserDomain
    suspend fun logout()
}

interface PriceTypes {
    suspend fun requestPriceTypes()
    suspend fun insertPriceTypes(priceTypesDto: List<PriceTypeDto>)
    suspend fun getPriceTypes(): List<PriceTypeDomain>
}

interface Categories {
    suspend fun requestCategories()
    suspend fun insertCategories(categoriesDto: List<CategoryDto>)
    suspend fun getCategories(): List<CategoryDomain>
}

interface Subcategories {
    suspend fun requestSubcategories(categoryId: Int)
    suspend fun insertSubcategories(subcategoriesDto: List<SubcategoryDto>, categoryId: Int)
    suspend fun getSubcategories(categoryId: Int): List<SubcategoryDomain>
}

interface Service {
    suspend fun insertAllServices()
    suspend fun updateService(serviceId: Int)
    fun getServices(): Flow<List<ServiceDomain>>
    suspend fun getService(serviceId: Int): Flow<ServiceDomain>
    suspend fun getServiceImage(photoPath: String): String?
    suspend fun updateServiceImage(serviceId: Int ,imageBase64: String)
    suspend fun updateService(serviceId: Int, service: NewServiceDomain)
    suspend fun deleteService(serviceId: Int)
    suspend fun createService(newService: NewServiceDomain)
    suspend fun requestService(serviceId: Int): ServiceDto
}

interface Formats {
    suspend fun requestFormats()
    suspend fun insertFormatsList(formatsDto: List<FormatsDto>)
    //suspend fun getFormatsListEntity()
    suspend fun getFormats(): List<FormatsDomain>
}

interface MainServices {
    suspend fun requestServices(page: Int, size: Int): List<ServiceDomain>
    suspend fun requestMainService(serviceId: Int): ServiceDomain
    suspend fun fetchServicesByCategory(page: Int, size: Int, serviceId: Int): List<ServiceDomain>
}

class DataManager @Inject constructor(
    private val authRepository: AuthRepository,
    private val offerRepository: OfferRepository,
    private val userRepository: IUserRepository,
    private val serviceRepository: IServiceRepository,
    private val categoriesRepository: ICategoriesRepository,
    private val subcategoriesRepository: ISubcategoriesRepository,
    private val priceTypesRepository: IPriceTypesRepository,
    private val formatsRepository: FormatsRepository,
    private val globalAppState: GlobalAppState,
    private val secureTokenStorage: SecureTokenStorage,

    private val appDataBase: AppDataBase
): User, Categories, Subcategories, Service, Formats, PriceTypes, MainServices {

    override suspend fun requestServices(page: Int, size: Int): List<ServiceDomain> = coroutineScope { // Используем coroutineScope для структурированного параллелизма
        val requestServicesDtoResult = offerRepository.searchServices(page, size)
        requestServicesDtoResult.fold(
            onSuccess = { servicesDto ->
                if (servicesDto.services.isEmpty()) {
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
        ) as List<ServiceDomain>
    }

    override suspend fun requestMainService(serviceId: Int): ServiceDomain {
        val requestServiceResponse = offerRepository.getService(serviceId)
        requestServiceResponse.onSuccess { serviceDto ->
            val image = offerRepository.getServiceImage(serviceDto.photoPath?: "").getOrElse { "" }
            return serviceDto.toDomain(image)
        }
        return ServiceDomain.EMPTY
    }

    override suspend fun fetchServicesByCategory(
        page: Int,
        size: Int,
        serviceId: Int
    ): List<ServiceDomain> = coroutineScope {
        val requestServicesDtoResult = offerRepository.searchServices(page, size, categoryId = serviceId.toLong())
        requestServicesDtoResult.fold(
            onSuccess = { servicesDto ->
                if (servicesDto.services.isEmpty()) {
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
        ) as List<ServiceDomain>
    }


    override suspend fun fetchUserAvatar(userId: Int): String? {
        val fetchUserAvatar = authRepository.getAvatar(userId.toString()) //TODO: Изменить на Int
        fetchUserAvatar.onSuccess { userAvatar ->
            return userAvatar
        }
        return null
    }

    override suspend fun fetchUser(): UserEntity {
        val fetchUserDto = authRepository.getUser()
        fetchUserDto.onSuccess { userDto ->
            val userAvatar = fetchUserAvatar(userDto.id)
            return userDto.toEntity(userAvatar)
        }
        return UserEntity.EMPTY
    }

    override suspend fun insertUser() {
        val userEntity = fetchUser()
        userRepository.saveUser(userEntity)//TODO: Изменить на insert
    }

    override suspend fun getUserFlow(): Flow<UserDomain> {
        return userRepository.getUser().map { userEntity ->
            userEntity?.toDomain() ?: UserDomain.EMPTY
        }
    }

    override suspend fun getUser(): Flow<UserDomain> {

        val getUserResponse = authRepository.getUser()
        getUserResponse.onSuccess { userDto ->
            val avatar = getAvatar(userDto.avatarPath?: "")
            userRepository.saveUser(userDto.toEntity(avatar))
        }.onFailure { failure ->
            return userRepository.getUser().map { entity ->
                entity?.toDomain() ?: UserDomain.EMPTY
            }
        }

        return userRepository.getUser().map { entity ->
            entity?.toDomain() ?: UserDomain.EMPTY
        }
    }

    override suspend fun getUserById(userId: Int): UserDomain {
        val getUserResponse = authRepository.getUserById(userId)
        getUserResponse.onSuccess { userListDto ->
            val userImage = getAvatar(userListDto.avatarPath?: "")
            return userListDto.toDomain(userImage)
        }
        return UserDomain.EMPTY
    }

    override suspend fun logout() {
        globalAppState.setLoadingState()
        Log.d("LOGOUT", "LOGOUT_STARTED")
        //userRepository.clearUser()
        withContext(Dispatchers.IO) { // Гарантируем выполнение на фоновом потоке IO
            appDataBase.clearAllTables()
            Log.d("DataManager", "All tables cleared from the database.")
            // Здесь можно добавить дополнительную логику после очистки,
            // например, сброс каких-либо кэшированных данных в DataManager, если они есть
            // или уведомление ViewModel для обновления UI (уже на Dispatchers.Main)
        }
        Log.d("LOGOUT", "USER_DATA_CLEARED")
        secureTokenStorage.clearTokens()
        Log.d("LOGOUT", "TOKENS_CLEARED")
        globalAppState.setAuthState()
        Log.d("LOGOUT", "LOGOUT_COMPLETE")
    }

    override suspend fun updateUser(userDomain: UserDomain) {
        authRepository.updateUser(userDomain.toDto()).onSuccess { response ->
            userRepository.saveUser(response.toEntity(userDomain.avatar))
        }.onFailure { response ->
            Log.d("UPDATE_USER_RESPONSE", "${response.message}")
        }
    }

    override suspend fun getAvatar(avatarPath: String): String? {
        return authRepository.getAvatar(avatarPath).getOrNull()
    }

    override suspend fun updateAvatar(base64: String) {
        val decodedBytes = Base64.decode(base64, Base64.DEFAULT)
        val requestBody = decodedBytes.toRequestBody("image/jpg".toMediaTypeOrNull())
        val file = MultipartBody.Part.createFormData("multipartFile", "avatar.jpg", requestBody)
        authRepository.updateAvatar(file).onSuccess { success ->
            userRepository.updateAvatar(
                avatar = base64,
                avatarPath = success.avatarPath
            )
        }
    }



    override suspend fun requestCategories() {
        val requestCategoriesDto = offerRepository.getCategories()
        requestCategoriesDto.onSuccess { categoriesDto ->
            insertCategories(categoriesDto)
        }
    }

    override suspend fun insertCategories(categoriesDto: List<CategoryDto>) {
        categoriesRepository.insertCategories(categoriesDtoListToEntityList(categoriesDto))
    }

    override suspend fun getCategories(): List<CategoryDomain> {
        return categoriesEntityListToDomainList(categoriesRepository.getCategories())
    }



    override suspend fun requestSubcategories(categoryId: Int) {
        val requestSubcategoriesDto = offerRepository.getSubcategories(categoryId)
        requestSubcategoriesDto.onSuccess { subcategoriesDto ->
            insertSubcategories(
                subcategoriesDto = subcategoriesDto,
                categoryId = categoryId
            )
        }
    }

    override suspend fun getSubcategories(categoryId: Int): List<SubcategoryDomain> {
        return subcategoriesEntityListToDomainList(subcategoriesRepository.getSubcategories(categoryId))
    }

    override suspend fun insertSubcategories(subcategoriesDto: List<SubcategoryDto>, categoryId: Int) {
        subcategoriesRepository.insertSubcategories(subcategoriesDtoListToEntityList(
            subcategoriesDto = subcategoriesDto,
            categoryId = categoryId
        ))
    }



    override fun getServices(): Flow<List<ServiceDomain>> {
        return serviceEntityListToDomainList(serviceRepository.getServices())
    }

    override suspend fun updateServiceImage(serviceId: Int, imageBase64: String) {
        val decodedBytes = Base64.decode(imageBase64, Base64.DEFAULT)
        val requestBody = decodedBytes.toRequestBody("image/jpg".toMediaTypeOrNull())
        val file = MultipartBody.Part.createFormData("multipartFile", "serviceImage.jpg", requestBody)
        offerRepository.updateServiceImage(serviceId, file).onSuccess { success ->
            serviceRepository.updateServiceImage(image = imageBase64, imagePath = success.photoPath, serviceId = serviceId)
        }
    }

    override suspend fun requestService(serviceId: Int): ServiceDto {
        return offerRepository.getService(serviceId).getOrElse { ServiceDto.EMPTY }
    }

    override suspend fun updateService(serviceId: Int, service: NewServiceDomain) {
        val updateServiceRequest = offerRepository.updateService(service.toDto(), serviceId)
        updateServiceRequest.onSuccess {
            updateService(serviceId)
        }
    }

    override suspend fun getService(serviceId: Int): Flow<ServiceDomain> {
        updateService(serviceId)
        return serviceEntityFlowToDomainFlow(serviceRepository.getService(serviceId))
    }

    override suspend fun getServiceImage(photoPath: String): String? {
        return offerRepository.getServiceImage(photoPath).getOrNull()
    }

    override suspend fun updateService(serviceId: Int) {
        val getServiceResponse = offerRepository.getService(serviceId)
        getServiceResponse.onSuccess { serviceDto ->
            val photo = getServiceImage(serviceDto.photoPath?: "")
            Log.d("UPDATE_SERVICE", serviceDto.formats.toString())
            serviceRepository.updateService(serviceDto.toEntity(photo))
        }
    }

    override suspend fun createService(newService: NewServiceDomain) {
        val createServiceResponse = offerRepository.createServiceRequest(newService.toDto())
        createServiceResponse.onSuccess {
            val newServiceResponse = requestService(it)
            serviceRepository.insertService(newServiceResponse.toEntity())
        }
    }

    override suspend fun deleteService(serviceId: Int) {
        val deleteServiceResponse = offerRepository.deleteService(serviceId)
        deleteServiceResponse.onSuccess {
            updateService(serviceId)
        }
    }

    override suspend fun insertAllServices() {
        val servicesResponse = offerRepository.searchUserServices(0, 20)
        servicesResponse.onSuccess { servicesDto ->
            val servicesEntity = servicesDto.map { serviceDto ->
                Log.d("SERVICE_DTO_RESPONSE", serviceDto.toString())
                serviceDto.toEntity()
            }
            Log.d("SERVICE_RESPONSE", servicesEntity.toString())
            serviceRepository.insertAllService(servicesEntity)
            //serviceRepository.insertService(servicesEntity[0])
        }
    }



    override suspend fun requestFormats() {
        val formatsDtoList = offerRepository.getFormats()
        formatsDtoList.onSuccess { formatsDto ->
            insertFormatsList(formatsDto)
        }
    }

    override suspend fun getFormats(): List<FormatsDomain> {
        requestFormats()
        return formatsEntityListToDomainList(formatsRepository.getFormats())
    }

    override suspend fun insertFormatsList(formatsDto: List<FormatsDto>) {
        formatsRepository.insertFormats(
            formatsDtoListToEntityList(dtoList = formatsDto)
        )
    }

    override suspend fun insertPriceTypes(priceTypesDto: List<PriceTypeDto>) {
        priceTypesRepository.insertPriceTypes(priceTypesDtoListToEntityList(priceTypesDto))
    }

    override suspend fun getPriceTypes(): List<PriceTypeDomain> {
        requestPriceTypes()
        return priceTypesEntityListToDomainList(priceTypesRepository.getPriceTypes())
    }

    override suspend fun requestPriceTypes() {
        val priceTypesRequest = offerRepository.getPriceTypes()
        priceTypesRequest.onSuccess { priceTypesDto ->
            insertPriceTypes(priceTypesDto)
        }
    }
}