package com.example.sellingserviceapp.model.mapper

import android.util.Log
import com.example.sellingserviceapp.model.entity.UserEntity
import com.example.sellingserviceapp.model.dto.UserDto
import com.example.sellingserviceapp.model.domain.UserDomain
import com.example.sellingserviceapp.model.mapper.UserConverters.toDomain

object UserConverters {
    fun UserDto.toEntity(avatar: String? = null): UserEntity {
        Log.d("USER_MAP", "Name = $firstName, SecondName = $secondName, LastName = $lastName")
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

fun usersDtoListToDomainList(usersDtoList: List<UserDto>): List<UserDomain> {
    return usersDtoList.map {
        it.toDomain()
    }
}