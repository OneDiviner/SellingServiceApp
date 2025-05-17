package com.example.sellingserviceapp.data

import android.util.Base64
import android.util.Log
import com.example.sellingserviceapp.data.di.GlobalAppState
import com.example.sellingserviceapp.data.di.SecureTokenStorage
import com.example.sellingserviceapp.data.local.repository.FormatsRepository
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
import com.example.sellingserviceapp.model.domain.FormatsDomain
import com.example.sellingserviceapp.model.domain.ServiceDomain
import com.example.sellingserviceapp.ui.screen.createService.model.Category
import com.example.sellingserviceapp.ui.screen.createService.model.Subcategory
import com.example.sellingserviceapp.model.domain.UserDomain
import com.example.sellingserviceapp.model.dto.FormatsDto
import com.example.sellingserviceapp.model.entity.ServiceEntity
import com.example.sellingserviceapp.model.mapper.ServiceConverters.toEntity
import com.example.sellingserviceapp.model.mapper.formatsDtoListToEntityList
import com.example.sellingserviceapp.model.mapper.formatsEntityListToDomainList
import com.example.sellingserviceapp.model.mapper.serviceEntityFlowToDomainFlow
import com.example.sellingserviceapp.model.mapper.serviceEntityListToDomainList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

interface User {
    suspend fun getUser(): Flow<UserDomain>
    suspend fun updateUser(userDomain: UserDomain)
    suspend fun getAvatar(avatarPath: String): String?
    suspend fun updateAvatar(base64: String)
    suspend fun logout()
}


interface Categories {
    suspend fun updateCategoriesWithSubcategories()
    suspend fun getCategories(): List<Category>
    suspend fun getSubcategories(categoryId: Int): List<Subcategory>
}

interface Service {
    suspend fun insertAllServices()
    suspend fun updateService(serviceId: Int)
    fun getServices(): Flow<List<ServiceDomain>>
    suspend fun getService(serviceId: Int): Flow<ServiceDomain>
    suspend fun getServiceImage(photoPath: String): String?
    suspend fun updateServiceImage(serviceId: Int ,imageBase64: String)
}

interface Formats {
    suspend fun requestFormats()
    suspend fun insertFormatsList(formatsDto: List<FormatsDto>)
    //suspend fun getFormatsListEntity()
    suspend fun getFormats(): List<FormatsDomain>
}

interface Initializer {
    suspend fun initialize()
}

class DataManager @Inject constructor(
    private val authRepository: AuthRepository,
    private val offerRepository: OfferRepository,
    private val userRepository: IUserRepository,
    private val serviceRepository: IServiceRepository,
    private val categoriesRepository: ICategoriesRepository,
    private val formatsRepository: FormatsRepository,
    private val globalAppState: GlobalAppState,
    private val secureTokenStorage: SecureTokenStorage
): User, Categories, Service, Formats, Initializer {

    override suspend fun initialize() {
        TODO("Not yet implemented")
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

    override suspend fun logout() {
        globalAppState.setLoadingState()
        Log.d("LOGOUT", "LOGOUT_STARTED")
        userRepository.clearUser()
        Log.d("LOGOUT", "USER_DATA_CLEARED")
        secureTokenStorage.clearTokens()
        Log.d("LOGOUT", "TOKENS_CLEARED")
        globalAppState.setAuthState()
        Log.d("LOGOUT", "LOGOUT_COMPLETE")
    }

    override suspend fun updateUser(userDomain: UserDomain) {
        authRepository.updateUser(userDomain.toDto()).onSuccess { response ->
            userRepository.saveUser(response.toEntity())
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

    override suspend fun updateCategoriesWithSubcategories() {
        val categoriesResponse = offerRepository.getCategories()
        val categories = categoriesResponse.getOrElse { emptyList() }
        categories.map { category ->
            val categoryEntity = CategoryEntity(
                id = category.id,
                name = category.categoryName,
                code = category.categoryCode
            )
            categoriesRepository.insertCategories(listOf(categoryEntity))
            val subcategoriesResponse = offerRepository.getSubcategories(categoryId = category.id)
            val subcategories = subcategoriesResponse.getOrElse { emptyList() }
            val subcategoryEntity = subcategories.map { subcategory ->
                SubcategoryEntity(
                    id = subcategory.id,
                    name = subcategory.subcategoryName,
                    code = subcategory.subcategoryCode,
                    categoryId = category.id
                )
            }
            categoriesRepository.insertSubcategories(subcategoryEntity)
        }

    }

    override suspend fun getCategories(): List<Category> {
        return categoriesRepository.getCategories()
    }

    override suspend fun getSubcategories(categoryId: Int): List<Subcategory> {
        return categoriesRepository.getSubcategories(categoryId)
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
}