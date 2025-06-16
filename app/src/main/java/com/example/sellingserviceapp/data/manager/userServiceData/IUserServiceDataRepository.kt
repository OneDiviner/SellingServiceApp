package com.example.sellingserviceapp.data.manager.userServiceData

import com.example.sellingserviceapp.model.domain.NewServiceDomain
import com.example.sellingserviceapp.model.domain.ServiceDomain
import com.example.sellingserviceapp.model.dto.ServiceDto
import kotlinx.coroutines.flow.Flow

interface IUserServiceDataRepository {
    suspend fun fetchUserServices(page: Int, size: Int)
    suspend fun insertAllServices(servicesDto: List<ServiceDto>)
    suspend fun updateService(serviceId: Int)
    fun getServices(): Flow<List<ServiceDomain>>
    suspend fun getService(serviceId: Int): Flow<ServiceDomain>
    suspend fun getServiceImage(photoPath: String): String?
    suspend fun updateServiceImage(serviceId: Int ,imageBase64: String)
    suspend fun updateService(serviceId: Int, service: NewServiceDomain)
    suspend fun deleteService(serviceId: Int)
    suspend fun createService(newService: NewServiceDomain)
    suspend fun requestService(serviceId: Int): ServiceDto
}