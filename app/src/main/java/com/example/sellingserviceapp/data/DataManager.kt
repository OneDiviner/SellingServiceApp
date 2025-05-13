package com.example.sellingserviceapp.data

import android.util.Base64
import android.util.Log
import com.example.sellingserviceapp.data.di.GlobalAppState
import com.example.sellingserviceapp.data.di.SecureTokenStorage
import com.example.sellingserviceapp.data.local.entity.CategoryEntity
import com.example.sellingserviceapp.data.local.entity.SubcategoryEntity
import com.example.sellingserviceapp.data.local.repository.categories.ICategoriesRepository
import com.example.sellingserviceapp.data.local.repository.user.IUserRepository
import com.example.sellingserviceapp.data.network.authorization.repository.AuthRepository
import com.example.sellingserviceapp.domain.converter.UserConverters.toDomain
import com.example.sellingserviceapp.domain.converter.UserConverters.toDto
import com.example.sellingserviceapp.domain.converter.UserConverters.toEntity
import com.example.sellingserviceapp.data.network.offer.repository.OfferRepository
import com.example.sellingserviceapp.ui.screen.createService.model.Category
import com.example.sellingserviceapp.ui.screen.createService.model.Subcategory
import com.example.sellingserviceapp.domain.UserDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

interface GetUser {
    suspend fun getUser(): Flow<UserDomain>
}

interface Logout {
    suspend fun logout()
}

interface UpdateUser {
    suspend fun updateUser(userDomain: UserDomain)
}

interface GetAvatar {
    suspend fun getAvatar(avatarPath: String): String?
}

interface UpdateAvatar {
    suspend fun updateAvatar(base64: String)
}

interface Service {
    suspend fun updateCategoriesWithSubcategories()
    suspend fun getCategories(): List<Category>
    suspend fun getSubcategories(categoryId: Int): List<Subcategory>
}

class DataManager @Inject constructor(
    private val authRepository: AuthRepository,
    private val offerRepository: OfferRepository,
    private val userRepository: IUserRepository,
    private val categoriesRepository: ICategoriesRepository,
    private val globalAppState: GlobalAppState,
    private val secureTokenStorage: SecureTokenStorage
): GetUser, Logout, UpdateUser, GetAvatar, UpdateAvatar, Service {
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
}