package com.example.sellingserviceapp.domain.converter

import com.example.sellingserviceapp.data.local.entity.UserEntity
import com.example.sellingserviceapp.data.network.dto.UserDto
import com.example.sellingserviceapp.domain.UserDomain

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