package com.example.sellingserviceapp.data.local.repository.categories

import com.example.sellingserviceapp.data.local.dao.CategoriesDao
import com.example.sellingserviceapp.data.local.entity.CategoryEntity
import com.example.sellingserviceapp.data.local.entity.SubcategoryEntity
import com.example.sellingserviceapp.ui.screen.createService.model.Category
import com.example.sellingserviceapp.ui.screen.createService.model.Subcategory
import javax.inject.Inject

class CategoriesRepository @Inject constructor (
    private val categoryDao: CategoriesDao
): ICategoriesRepository {
    override suspend fun insertCategories(categories: List<CategoryEntity>) {
        categoryDao.insertCategories(categories)
    }


    override suspend fun insertSubcategories(subcategories: List<SubcategoryEntity>) {
        categoryDao.insertSubcategories(subcategories)
    }

    override suspend fun getCategories(): List<Category> {
        return categoryDao.getCategories().map { categoryEntity ->
            Category(
                id = categoryEntity.id,
                categoryName = categoryEntity.name,
                categoryCode = categoryEntity.code
            )
        }
    }

    override suspend fun getSubcategories(categoryId: Int): List<Subcategory> {
        return categoryDao.getSubcategories(categoryId).map { subcategoryEntity ->
            Subcategory(
                id = subcategoryEntity.id,
                subcategoryName = subcategoryEntity.name,
                subcategoryCode = subcategoryEntity.code
            )
        }
    }
}