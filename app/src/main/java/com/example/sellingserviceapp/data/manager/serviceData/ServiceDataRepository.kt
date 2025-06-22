package com.example.sellingserviceapp.data.manager.serviceData

import com.example.sellingserviceapp.data.network.offer.repository.OfferRepository
import com.example.sellingserviceapp.model.domain.ServiceDomain
import com.example.sellingserviceapp.model.mapper.ServiceConverters.toDomain
import com.example.sellingserviceapp.model.mapper.serviceDtoListToDomainList
import javax.inject.Inject

class ServiceDataRepository @Inject constructor(
    private val offerRepository: OfferRepository,
) : IServiceDataRepository {

    override suspend fun requestMainService(serviceId: Int): ServiceDomain {
        val requestServiceResponse = offerRepository.getService(serviceId)
        requestServiceResponse.onSuccess { serviceDto ->
            val image = offerRepository.getServiceImage(serviceDto.photoPath?: "").getOrElse { "" }
            return serviceDto.toDomain(image)
        }
        return ServiceDomain.EMPTY
    }

    override suspend fun fetchServiceList(serviceIds: List<Int>): List<ServiceDomain> {
        val fetchServiceListResponse = offerRepository.getServicesList(serviceIds)
        fetchServiceListResponse.onSuccess {
            return serviceDtoListToDomainList(it)
        }
        return emptyList()
    }

}