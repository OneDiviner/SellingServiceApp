package com.example.sellingserviceapp.model.mapper

import com.example.sellingserviceapp.model.entity.UserEntity
import com.example.sellingserviceapp.model.dto.UserDto
import com.example.sellingserviceapp.model.domain.UserDomain

object UserConverters {
    fun UserDto.toEntity(avatar: String? = null): UserEntity {
        return UserEntity(
            id = id,
            firstName = firstName,
            secondName = secondName,
            lastName = lastName,
            email = email,
            phoneNumber = phoneNumber,
            avatar = avatar,
            avatarPath = avatarPath
        )
    }
    fun UserDto.toDomain(userPhoto: String? = null): UserDomain {
        return UserDomain(
            email = email,
            firstName = firstName,
            secondName = secondName,
            lastName = lastName,
            phoneNumber = phoneNumber,
            avatar = userPhoto
        )
    }
    fun UserEntity.toDomain(): UserDomain {
        return UserDomain(
            email = email,
            firstName = firstName,
            secondName = secondName,
            lastName = lastName,
            phoneNumber = phoneNumber,
            avatar = avatar
        )
    }
    fun UserDomain.toDto(): UserDto {
        return UserDto(
            id = 0,
            email = email,
            firstName = firstName,
            secondName = secondName,
            lastName = lastName,
            phoneNumber = phoneNumber,
            avatarPath = "",
            createdAt = "",
            updatedAt = "",
            role = "",
            status = ""
        )
    }
}