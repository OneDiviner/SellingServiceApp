package com.example.sellingserviceapp.model.mapper

import android.util.Log
import com.example.sellingserviceapp.model.domain.ServiceDomain
import com.example.sellingserviceapp.model.entity.ServiceEntity
import com.example.sellingserviceapp.model.dto.ServiceDto
import com.example.sellingserviceapp.model.mapper.ServiceConverters.toDomain
import com.example.sellingserviceapp.model.mapper.ServiceConverters.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object ServiceConverters {

    private val priceTypeMap = mapOf(
        "PRICE_TYPE_FOR_OFFER" to "услугу",
        "PRICE_TYPE_FOR_KILOGRAM" to "киллограмм",
        "PRICE_TYPE_FOR_METER" to "метр",
        "PRICE_TYPE_FOR_SQUARE_METER" to "м^2",
        "PRICE_TYPE_FOR_PIECE" to "штуку",
        "PRICE_TYPE_FOR_LITER" to "литр",
        "PRICE_TYPE_FOR_HOUR" to "час",
    )


    private val statusMap = mapOf(
        "STATUS_NOT_ACTIVE" to "Скрыта",
        "STATUS_ACTIVE" to "Активна",
        "STATUS_DELETED" to "Удалена"
    )

    fun ServiceDto.toEntity(photo: String? = null): ServiceEntity {
        Log.d("DTO_TO_ENTITY", formats?.toString()?: "Noting")
        return ServiceEntity(
            id = id,
            userId = userId,
            tittle = tittle,
            description = description,
            duration = duration,
            photoPath = photoPath,
            photo = photo,
            price = price,
            priceTypeCode = priceType,
            priceTypeName = priceTypeMap[priceType]?: priceType,
            statusCode = status,
            statusName = statusMap[status]?: status,
            createdAt = createdAt,
            updatedAt = updatedAt,
            formats = formatsDtoListToEntityList(formats),
            categoryCode = category,
            categoryName = CategoryMapper.map(category),
            subcategoryCode = subcategory,
            subcategoryName = SubcategoryMapper.map(subcategory)
        )
    }

    fun ServiceDto.toDomain(photo: String? = null): ServiceDomain {
        return ServiceDomain(
            id = id,
            userId = userId,
            tittle = tittle,
            description = description,
            duration = duration,
            photoPath = photoPath,
            photo = photo,
            price = price,
            priceTypeCode = priceType,
            priceTypeName = priceTypeMap[priceType]?: priceType,
            statusCode = status,
            statusName = statusMap[status]?: status,
            createdAt = createdAt,
            updatedAt = updatedAt,
            formats = formatsDtoListToDomainList(formats),
            categoryCode = category,
            categoryName = CategoryMapper.map(category),
            subcategoryCode = subcategory,
            subcategoryName = SubcategoryMapper.map(subcategory)
        )
    }

    fun ServiceEntity.toDomain(): ServiceDomain {
        return ServiceDomain(
            id = id,
            userId = userId,
            tittle = tittle,
            description = description,
            duration = duration,
            photoPath = photoPath,
            photo = photo,
            price = price,
            priceTypeCode = priceTypeCode,
            priceTypeName = priceTypeName,
            statusCode = statusCode,
            statusName = statusName,
            createdAt = createdAt,
            updatedAt = updatedAt,
            formats = formatsEntityListToDomainList(formats),
            categoryCode = categoryCode,
            categoryName = categoryName,
            subcategoryCode = subcategoryCode,
            subcategoryName = subcategoryName
        )
    }
}

fun serviceDtoListToDomainList(serviceDtoList: List<ServiceDto>?, serviceImage: String? = null): List<ServiceDomain> {
    return serviceDtoList?.map {
        it.toDomain(serviceImage)
    } ?: emptyList()
}

fun serviceEntityListToDomainList(serviceEntityFlow: Flow<List<ServiceEntity>>): Flow<List<ServiceDomain>> {
    return serviceEntityFlow.map { serviceEntityList ->
        serviceEntityList.map {
            it.toDomain()
        }
    }
}

fun serviceEntityFlowToDomainFlow(serviceEntityFlow: Flow<ServiceEntity>): Flow<ServiceDomain> {
    return serviceEntityFlow.map { serviceEntity ->
        serviceEntity.toDomain()
    }
}

fun serviceDtoListToEntityList(serviceDtoList: List<ServiceDto>?): List<ServiceEntity> {
    return serviceDtoList?.map {
        it.toEntity()
    }?: emptyList()
}
