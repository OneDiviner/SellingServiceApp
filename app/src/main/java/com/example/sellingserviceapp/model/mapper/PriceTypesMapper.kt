package com.example.sellingserviceapp.model.mapper

import com.example.sellingserviceapp.model.domain.FormatsDomain
import com.example.sellingserviceapp.model.domain.PriceTypeDomain
import com.example.sellingserviceapp.model.dto.FormatsDto
import com.example.sellingserviceapp.model.dto.PriceTypeDto
import com.example.sellingserviceapp.model.entity.FormatsEntity
import com.example.sellingserviceapp.model.entity.PriceTypeEntity
import com.example.sellingserviceapp.model.mapper.PriceTypesMapper.toDomain
import com.example.sellingserviceapp.model.mapper.PriceTypesMapper.toEntity

private val priceTypeMap = mapOf(
    "PRICE_TYPE_FOR_OFFER" to "Усл.",
    "PRICE_TYPE_FOR_KILOGRAM" to "Кг",
    "PRICE_TYPE_FOR_METER" to "М",
    "PRICE_TYPE_FOR_SQUARE_METER" to "М^2",
    "PRICE_TYPE_FOR_PIECE" to "Шт.",
    "PRICE_TYPE_FOR_LITER" to "Литр",
    "PRICE_TYPE_FOR_HOUR" to "Час",
)

object PriceTypesMapper {
    fun PriceTypeDto.toEntity(): PriceTypeEntity {
        val name = priceTypeMap[code] ?: this.code
        return PriceTypeEntity(
            id = id,
            name = name,
            code = code
        )
    }

    fun PriceTypeEntity.toDomain(): PriceTypeDomain {
        return PriceTypeDomain(
            id = id,
            name = name,
            code = code
        )
    }
}

fun priceTypesDtoListToEntityList(priceTypesDto: List<PriceTypeDto>): List<PriceTypeEntity> {
    return priceTypesDto.map {
        it.toEntity()
    }
}

fun priceTypesEntityListToDomainList(priceTypeEntity: List<PriceTypeEntity>): List<PriceTypeDomain> {
    return priceTypeEntity.map {
        it.toDomain()
    }
}