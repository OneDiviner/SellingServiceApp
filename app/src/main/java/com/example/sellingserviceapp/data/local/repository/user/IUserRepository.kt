package com.example.sellingserviceapp.data.local.repository.user

import com.example.sellingserviceapp.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    suspend fun saveUser(userEntity: UserEntity)
    suspend fun getUser(): Flow<UserEntity?>
    suspend fun updateAvatar(avatar: String?, avatarPath: String?)
    suspend fun clearUser()
}