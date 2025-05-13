package com.example.sellingserviceapp.data.local.repository.categories

import com.example.sellingserviceapp.data.local.entity.CategoryEntity
import com.example.sellingserviceapp.data.local.entity.SubcategoryEntity
import com.example.sellingserviceapp.ui.screen.createService.model.Category
import com.example.sellingserviceapp.ui.screen.createService.model.Subcategory

interface ICategoriesRepository {
    suspend fun insertCategories(categories: List<CategoryEntity>)
    suspend fun insertSubcategories(subcategories: List<SubcategoryEntity>)
    suspend fun getCategories(): List<Category>
    suspend fun getSubcategories(categoryId: Int): List<Subcategory>
    //suspend fun insertCategoriesWithSubcategories(categories: List<CategoryEntity>, subcategories: List<SubcategoryEntity>)
}