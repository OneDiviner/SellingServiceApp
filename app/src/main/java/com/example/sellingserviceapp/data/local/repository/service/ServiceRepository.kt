package com.example.sellingserviceapp.data.local.repository.service

import com.example.sellingserviceapp.data.local.dao.ServiceDao
import com.example.sellingserviceapp.model.entity.ServiceEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface IServiceRepository {
    suspend fun insertAllService(serviceEntity: List<ServiceEntity>)
    suspend fun insertService(serviceEntity: ServiceEntity)
    fun getServices(): Flow<List<ServiceEntity>>
    fun getService(serviceId: Int): Flow<ServiceEntity>
    suspend fun changedStatusToDelete(serviceId: Int)
    suspend fun updateService(serviceEntity: ServiceEntity)
    suspend fun updateServiceImage(image: String?, imagePath: String?, serviceId: Int)
}

class ServiceRepository @Inject constructor(
    private val serviceDao: ServiceDao
): IServiceRepository {
    override suspend fun insertAllService(serviceEntity: List<ServiceEntity>) {
        serviceDao.insertAll(serviceEntity)
    }

    override suspend fun insertService(serviceEntity: ServiceEntity) {
        serviceDao.insert(serviceEntity)
    }

    override fun getServices(): Flow<List<ServiceEntity>> {
        return serviceDao.getServices()
    }

    override fun getService(serviceId: Int): Flow<ServiceEntity> {
        return serviceDao.getService(serviceId)
    }

    override suspend fun updateService(serviceEntity: ServiceEntity) {
        serviceDao.update(serviceEntity)
    }

    override suspend fun updateServiceImage(image: String?, imagePath: String?, serviceId: Int) {
        serviceDao.updateServiceImage(
            photo = image,
            photoPath = imagePath,
            serviceId = serviceId
        )
    }

    override suspend fun changedStatusToDelete(serviceId: Int) {
        serviceDao.changeStatusToDelete(serviceId)
    }
}