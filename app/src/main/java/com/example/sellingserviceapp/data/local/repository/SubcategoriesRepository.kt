package com.example.sellingserviceapp.data.local.repository

import com.example.sellingserviceapp.data.local.dao.SubcategoriesDao
import com.example.sellingserviceapp.model.entity.SubcategoryEntity
import com.example.sellingserviceapp.ui.screen.createService.model.Subcategory
import javax.inject.Inject

interface ISubcategoriesRepository {
    suspend fun insertSubcategories(subcategoriesEntity: List<SubcategoryEntity>)
    suspend fun getSubcategories(categoryId: Int): List<SubcategoryEntity>
}

class SubcategoriesRepository @Inject constructor(
    private val subcategoriesDao: SubcategoriesDao
): ISubcategoriesRepository {
    override suspend fun insertSubcategories(subcategoriesEntity: List<SubcategoryEntity>) {
        subcategoriesDao.insertSubcategories(subcategoriesEntity)
    }

    override suspend fun getSubcategories(categoryId: Int): List<SubcategoryEntity> {
        return subcategoriesDao.getSubcategories(categoryId)
    }
}