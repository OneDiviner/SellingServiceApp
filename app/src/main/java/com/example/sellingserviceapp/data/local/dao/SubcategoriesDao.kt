package com.example.sellingserviceapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sellingserviceapp.model.entity.SubcategoryEntity

@Dao
interface SubcategoriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubcategories(subcategories: List<SubcategoryEntity>)

    @Query("SELECT * FROM subcategories WHERE category_id = :categoryId")
    suspend fun getSubcategories(categoryId: Int): List<SubcategoryEntity>
}