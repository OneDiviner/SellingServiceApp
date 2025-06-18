package com.example.sellingserviceapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.sellingserviceapp.model.entity.ServiceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(serviceEntity: ServiceEntity)

    @Update
    suspend fun update(serviceEntity: ServiceEntity)

    @Delete
    suspend fun delete(serviceEntity: ServiceEntity)

    @Query("UPDATE services SET status_code = 'STATUS_DELETED', status_name = 'Удалена' WHERE id = :serviceId")
    suspend fun changeStatusToDelete(serviceId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(serviceEntities: List<ServiceEntity>)

    @Query("SELECT * FROM services WHERE status_code != 'STATUS_DELETED'")
    fun getServices(): Flow<List<ServiceEntity>>

    @Query("SELECT * FROM services WHERE id = :serviceId")
    fun getService(serviceId: Int): Flow<ServiceEntity>

    @Query("UPDATE services SET photo = :photo, photo_path = :photoPath WHERE id = :serviceId")
    suspend fun updateServiceImage(
        photo: String?,
        photoPath: String?,
        serviceId: Int
    )

}