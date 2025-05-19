package com.example.sellingserviceapp.data.local.repository.categories

import com.example.sellingserviceapp.data.local.dao.CategoriesDao
import com.example.sellingserviceapp.model.entity.CategoryEntity
import com.example.sellingserviceapp.model.entity.SubcategoryEntity
import com.example.sellingserviceapp.ui.screen.createService.model.Category
import com.example.sellingserviceapp.ui.screen.createService.model.Subcategory
import javax.inject.Inject

class CategoriesRepository @Inject constructor (
    private val categoryDao: CategoriesDao
): ICategoriesRepository {
    override suspend fun insertCategories(categoriesEntity: List<CategoryEntity>) {
        categoryDao.insertCategories(categoriesEntity)
    }

    override suspend fun getCategories(): List<CategoryEntity> {
        return categoryDao.getCategories()
    }

}