package com.example.sellingserviceapp.data.manager.userData

import com.example.sellingserviceapp.model.domain.UserDomain
import com.example.sellingserviceapp.model.dto.UserDto
import kotlinx.coroutines.flow.Flow

interface IUserDataRepository {
    suspend fun fetchUser()
    suspend fun fetchUserList(userIds: List<Int>) : List<UserDto>
    suspend fun fetchUserAvatar(avatarPath: String): String?
    suspend fun insertUser(userDto: UserDto, avatar: String?)
    suspend fun getUserFlow(): Flow<UserDomain>
    suspend fun fetchUserById(userId: Int): UserDomain
    suspend fun updateUser(userDomain: UserDomain)
    suspend fun updateAvatar(base64: String)
    suspend fun logout()
}