package com.example.sellingserviceapp.model.mapper

import com.example.sellingserviceapp.model.domain.CategoryDomain
import com.example.sellingserviceapp.model.dto.CategoryDto
import com.example.sellingserviceapp.model.entity.CategoryEntity
import com.example.sellingserviceapp.model.mapper.CategoryMapper.toDomain
import com.example.sellingserviceapp.model.mapper.CategoryMapper.toEntity

private val categoryMap = mapOf(
    "CATEGORY_REPAIR" to "Ремонт",
    "CATEGORY_MAINTENANCE" to "Обслуживание",
    "CATEGORY_EDUCATION" to "Образование"
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