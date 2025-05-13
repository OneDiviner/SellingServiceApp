package com.example.sellingserviceapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.example.sellingserviceapp.data.local.entity.ServiceEntity

@Dao
interface ServiceDao {
    @Insert
    suspend fun insert(serviceEntity: ServiceEntity)

    @Update
    suspend fun update(serviceEntity: ServiceEntity)

    @Delete
    suspend fun delete(serviceEntity: ServiceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(serviceEntities: List<ServiceEntity>)
}