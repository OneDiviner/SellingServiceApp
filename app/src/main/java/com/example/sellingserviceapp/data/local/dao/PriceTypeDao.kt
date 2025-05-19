package com.example.sellingserviceapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sellingserviceapp.model.entity.FormatsEntity
import com.example.sellingserviceapp.model.entity.PriceTypeEntity

@Dao
interface PriceTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPriceTypes(priceTypes: List<PriceTypeEntity>)

    @Query("SELECT * FROM price_types")
    suspend fun getPriceTypes(): List<PriceTypeEntity>
}