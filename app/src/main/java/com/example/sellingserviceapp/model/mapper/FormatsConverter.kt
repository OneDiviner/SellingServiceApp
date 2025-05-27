package com.example.sellingserviceapp.model.mapper

import androidx.room.TypeConverter
import com.example.sellingserviceapp.model.domain.FormatsDomain
import com.example.sellingserviceapp.model.dto.FormatsDto
import com.example.sellingserviceapp.model.entity.FormatsEntity
import com.example.sellingserviceapp.model.mapper.FormatsConverters.toDomain
import com.example.sellingserviceapp.model.mapper.FormatsConverters.toEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private val formatMap = mapOf(
    "LOCATION_TYPE_OFFLINE" to "Выезжаю к клиенту",
    "LOCATION_TYPE_ONLINE" to "Работаю дистанционно",
    "LOCATION_TYPE_POSITION" to "Работаю у себя"
)

object FormatsConverters {
    private val gson = Gson()
    private val type = object : TypeToken<List<FormatsEntity>>() {}.type

    @TypeConverter
    fun toJson(formats: List<FormatsEntity>?): String? {
        return if (formats.isNullOrEmpty()) null else gson.toJson(formats)
    }

    @TypeConverter
    fun toFormatsList(json: String?): List<FormatsEntity>? {
        return try {
            json?.takeIf { it.isNotBlank() }?.let {
                gson.fromJson(it, type) ?: emptyList()
            } ?: emptyList()
        } catch (e: Exception) {
            emptyList() // Возвращаем пустой список в случае ошибки
        }
    }

    fun FormatsDto.toEntity(): FormatsEntity {
        val name = formatMap[code] ?: this.code
        return FormatsEntity(
            id = id,
            name = name,
            code = code,
            address = address,
            isPhysical = isPhysical
        )
    }

    fun FormatsDto.toDomain(): FormatsDomain {
        val name = formatMap[code] ?: this.code
        return FormatsDomain(
            id = id,
            name = name,
            code = code,
            address = address,
            isPhysical = isPhysical
        )
    }

    fun FormatsEntity.toDomain(): FormatsDomain {
        return FormatsDomain(
            id = id,
            name = name,
            code = code,
            address = address,
            isPhysical = isPhysical
        )
    }
}

fun formatsDtoListToDomainList(dtoList: List<FormatsDto>?): List<FormatsDomain> {
    return dtoList?.map {
        it.toDomain()
    }?: emptyList()
}

fun formatsDtoListToEntityList(dtoList: List<FormatsDto>?): List<FormatsEntity> {
    return dtoList?.map {
        it.toEntity()
    }?: emptyList()
}

fun formatsEntityListToDomainList(formatsEntityList: List<FormatsEntity>?): List<FormatsDomain> {
    return formatsEntityList?.map {
        it.toDomain()
    }?: emptyList()
}