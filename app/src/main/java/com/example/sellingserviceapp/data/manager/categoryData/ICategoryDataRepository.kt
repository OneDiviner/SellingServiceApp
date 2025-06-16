package com.example.sellingserviceapp.data.manager.categoryData

import com.example.sellingserviceapp.model.domain.CategoryDomain
import com.example.sellingserviceapp.model.dto.CategoryDto

interface ICategoryDataRepository {
    suspend fun fetchCategories()
    suspend fun insertCategories(categoriesDto: List<CategoryDto>)
    suspend fun getCategories(): List<CategoryDomain>
}