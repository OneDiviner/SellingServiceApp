package com.example.sellingserviceapp.data.local.repository.user

import com.example.sellingserviceapp.data.local.dao.UserDao
import com.example.sellingserviceapp.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
): IUserRepository {
    override suspend fun saveUser(userEntity: UserEntity) {
        userDao.saveUser(userEntity = userEntity)
    }

    override suspend fun getUser(): Flow<UserEntity?> {
        return userDao.getUser()
    }

    override suspend fun updateAvatar(avatar: String?, avatarPath: String?) {
        userDao.updateUserAvatar(avatar, avatarPath)
    }

    override suspend fun clearUser() {
        userDao.clearUser()
    }
}