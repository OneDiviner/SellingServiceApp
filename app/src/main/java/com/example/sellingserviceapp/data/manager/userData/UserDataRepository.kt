package com.example.sellingserviceapp.data.manager.userData

import android.util.Base64
import android.util.Log
import com.example.sellingserviceapp.data.di.GlobalAppState
import com.example.sellingserviceapp.data.di.SecureTokenStorage
import com.example.sellingserviceapp.data.local.AppDataBase
import com.example.sellingserviceapp.data.local.repository.user.IUserRepository
import com.example.sellingserviceapp.data.network.authorization.repository.AuthRepository
import com.example.sellingserviceapp.model.domain.UserDomain
import com.example.sellingserviceapp.model.dto.UserDto
import com.example.sellingserviceapp.model.mapper.UserConverters.toDomain
import com.example.sellingserviceapp.model.mapper.UserConverters.toDto
import com.example.sellingserviceapp.model.mapper.UserConverters.toEntity
import com.example.sellingserviceapp.model.mapper.usersDtoListToDomainList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class UserDataRepository @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: IUserRepository,
    private val globalAppState: GlobalAppState,
    private val secureTokenStorage: SecureTokenStorage,
    private val appDataBase: AppDataBase
): IUserDataRepository {

    override suspend fun fetchUserAvatar(avatarPath: String): String? {
        val fetchUserAvatar = authRepository.getAvatar(avatarPath) //TODO: Изменить на Int
        fetchUserAvatar.onSuccess { userAvatar ->
            return userAvatar
        }
        return null
    }

    override suspend fun fetchUser() {
        val fetchUserDto = authRepository.getUser()
        fetchUserDto.onSuccess { userDto ->
            val userAvatar = fetchUserAvatar(userDto.avatarPath?: "")
            Log.d("FETCH_USER", "Secondname = ${userDto.secondName}, lastname = ${userDto.lastName}")
            insertUser(userDto, userAvatar)
        }
    }

    override suspend fun insertUser(userDto: UserDto, avatar: String?) {
        val userEntity = userDto.toEntity(avatar)
        Log.d("INSERT_USER", "SUCCESS")
        userRepository.saveUser(userEntity)//TODO: Изменить на insert
    }

    override suspend fun getUserFlow(): Flow<UserDomain> {
        return userRepository.getUser().map { userEntity ->
            userEntity?.toDomain() ?: UserDomain.EMPTY
        }
    }

    override suspend fun fetchUserById(userId: Int): UserDomain {
        val getUserResponse = authRepository.getUserById(userId)
        getUserResponse.onSuccess { userListDto ->
            val userImage = fetchUserAvatar(userListDto.avatarPath?: "")
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

    override suspend fun fetchUserList(userIds: List<Int>): List<UserDto> {
        val getUserListResponse = authRepository.getUsersListById(userIds)
        getUserListResponse.onSuccess {
            return it
        }
        return  emptyList()
    }

}