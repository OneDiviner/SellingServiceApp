package com.example.sellingserviceapp.data.manager.subcategoryData

import com.example.sellingserviceapp.model.domain.SubcategoryDomain
import com.example.sellingserviceapp.model.dto.SubcategoryDto

interface ISubcategoryDataRepository {
    suspend fun requestSubcategories(categoryId: Int)
    suspend fun insertSubcategories(subcategoriesDto: List<SubcategoryDto>, categoryId: Int)
    suspend fun getSubcategories(categoryId: Int): List<SubcategoryDomain>
}