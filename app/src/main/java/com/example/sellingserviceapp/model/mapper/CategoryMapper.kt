package com.example.sellingserviceapp.model.mapper

import com.example.sellingserviceapp.model.domain.CategoryDomain
import com.example.sellingserviceapp.model.dto.CategoryDto
import com.example.sellingserviceapp.model.entity.CategoryEntity
import com.example.sellingserviceapp.model.mapper.CategoryMapper.toDomain
import com.example.sellingserviceapp.model.mapper.CategoryMapper.toEntity

private val categoryMap = mapOf(
    "CATEGORY_REPAIR" to "Ремонт и отделка",
    "CATEGORY_TECH_REPAIR" to "Ремонт и обслуживание техники",
    "CATEGORY_EDUCATION" to "Образование",
    "CATEGORY_CLEANING" to "Уборка",
    "CATEGORY_CARGO" to "Грузовые перевозки",
    "CATEGORY_AUTO_SERVICE" to "Автосервис",
    "CATEGORY_BEAUTY" to "Красота",
    "CATEGORY_IT" to "IT, дизайн, маркетинг",
    "CATEGORY_EVENTS" to "Праздники, мероприятия",
    "CATEGORY_PHOTO_VIDEO" to "Фото и видеосъёмка",
    "CATEGORY_SECURITY" to "Охрана, безопасность",
    "CATEGORY_CONSTRUCTION" to "Строительство"
)

object CategoryMapper {
    fun CategoryDto.toEntity(): CategoryEntity {
        val name = categoryMap[code]?: code
        return CategoryEntity(
            id = id,
            name = name,
            code = code
        )
    }
    fun CategoryEntity.toDomain(): CategoryDomain {
        return CategoryDomain(
            id = id,
            name = name,
            code = code
        )
    }
    fun map(categoryCode: String) : String {
        return categoryMap[categoryCode]?: categoryCode
    }
}

fun categoriesDtoListToEntityList(categoriesDto: List<CategoryDto>): List<CategoryEntity> {
    return categoriesDto.map {
        it.toEntity()
    }
}

fun categoriesEntityListToDomainList(categoriesEntity: List<CategoryEntity>): List<CategoryDomain> {
    return categoriesEntity.map {
        it.toDomain()
    }
}