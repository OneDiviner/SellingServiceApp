package com.example.sellingserviceapp.model.mapper

import android.util.Log
import com.example.sellingserviceapp.model.domain.ServiceDomain
import com.example.sellingserviceapp.model.entity.ServiceEntity
import com.example.sellingserviceapp.model.dto.ServiceDto
import com.example.sellingserviceapp.model.mapper.ServiceConverters.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object ServiceConverters {

    private val categoryMap = mapOf(
        "CATEGORY_REPAIR" to "Ремонт",
        "CATEGORY_MAINTENANCE" to "Обслуживание",
        "CATEGORY_EDUCATION" to "Образование"
    )

    private val subcategoryMap = mapOf(
        "SUBCATEGORY_REPAIR_CAR" to "Ремонт автомобилей",
        "SUBCATEGORY_REPAIR_HOUSEHOLD_APPLIANCES" to "Ремонт бытовой техники",
        "SUBCATEGORY_MAINTENANCE_WINDOW" to "Обслуживание окон",
        "SUBCATEGORY_MAINTENANCE_DOOR" to "Обслуживание дверей",
        "SUBCATEGORY_EDUCATION_COURSE" to "Курсы",
        "SUBCATEGORY_EDUCATION_TUTORING" to "Репетиторство"
    )

    private val priceTypeMap = mapOf(
        "PRICE_TYPE_FOR_OFFER" to "Усл.",
        "PRICE_TYPE_FOR_KILOGRAM" to "Кг",
        "PRICE_TYPE_FOR_METER" to "М",
        "PRICE_TYPE_FOR_SQUARE_METER" to "М^2",
        "PRICE_TYPE_FOR_PIECE" to "Шт.",
        "PRICE_TYPE_FOR_LITER" to "Литр",
        "PRICE_TYPE_FOR_HOUR" to "Час",
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
            categoryName = categoryMap[category]?: category,
            subcategoryCode = subcategory,
            subcategoryName = subcategoryMap[subcategory]?: subcategory
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
