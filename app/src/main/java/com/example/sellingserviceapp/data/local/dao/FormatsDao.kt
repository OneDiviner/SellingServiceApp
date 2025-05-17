package com.example.sellingserviceapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sellingserviceapp.model.entity.CategoryEntity
import com.example.sellingserviceapp.model.entity.FormatsEntity
import com.example.sellingserviceapp.model.entity.SubcategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FormatsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFormats(formats: List<FormatsEntity>)

    @Query("SELECT * FROM formats")
    suspend fun getFormats(): List<FormatsEntity>

}