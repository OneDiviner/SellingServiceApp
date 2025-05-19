package com.example.sellingserviceapp.data.local.repository.categories

import com.example.sellingserviceapp.model.entity.CategoryEntity
import com.example.sellingserviceapp.model.entity.SubcategoryEntity
import com.example.sellingserviceapp.ui.screen.createService.model.Category
import com.example.sellingserviceapp.ui.screen.createService.model.Subcategory

interface ICategoriesRepository {
    suspend fun insertCategories(categoriesEntity: List<CategoryEntity>)
    suspend fun getCategories(): List<CategoryEntity>
}