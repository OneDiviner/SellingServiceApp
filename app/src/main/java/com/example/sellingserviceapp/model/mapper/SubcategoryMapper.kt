package com.example.sellingserviceapp.model.mapper

import com.example.sellingserviceapp.model.domain.CategoryDomain
import com.example.sellingserviceapp.model.domain.SubcategoryDomain
import com.example.sellingserviceapp.model.dto.CategoryDto
import com.example.sellingserviceapp.model.dto.SubcategoryDto
import com.example.sellingserviceapp.model.entity.CategoryEntity
import com.example.sellingserviceapp.model.entity.SubcategoryEntity
import com.example.sellingserviceapp.model.mapper.SubcategoryMapper.toDomain
import com.example.sellingserviceapp.model.mapper.SubcategoryMapper.toEntity

private val subcategoryMap = mapOf(
    "SUBCATEGORY_REPAIR_CAR" to "Ремонт автомобилей",
    "SUBCATEGORY_REPAIR_HOUSEHOLD_APPLIANCES" to "Ремонт бытовой техники",
    "SUBCATEGORY_MAINTENANCE_WINDOW" to "Обслуживание окон",
    "SUBCATEGORY_MAINTENANCE_DOOR" to "Обслуживание дверей",
    "SUBCATEGORY_EDUCATION_COURSE" to "Курсы",
    "SUBCATEGORY_EDUCATION_TUTORING" to "Репетиторство"
)

object SubcategoryMapper {
    fun SubcategoryDto.toEntity(categoryId: Int): SubcategoryEntity {
        val name = subcategoryMap[code]?: code
        return SubcategoryEntity(
            id = id,
            name = name,
            code = code,
            categoryId = categoryId
        )
    }
    fun SubcategoryEntity.toDomain(): SubcategoryDomain {
        return SubcategoryDomain(
            id = id,
            name = name,
            code = code
        )
    }
}

fun subcategoriesDtoListToEntityList(subcategoriesDto: List<SubcategoryDto>, categoryId: Int): List<SubcategoryEntity> {
    return subcategoriesDto.map {
        it.toEntity(categoryId)
    }
}

fun subcategoriesEntityListToDomainList(subcategoriesEntity: List<SubcategoryEntity>): List<SubcategoryDomain> {
    return subcategoriesEntity.map {
        it.toDomain()
    }
}