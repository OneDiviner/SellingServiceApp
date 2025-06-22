package com.example.sellingserviceapp.data.manager.serviceData

import com.example.sellingserviceapp.model.domain.ServiceDomain

interface IServiceDataRepository {
    suspend fun requestMainService(serviceId: Int): ServiceDomain
    suspend fun fetchServiceList(serviceIds: List<Int>): List<ServiceDomain>
}