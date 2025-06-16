package com.example.sellingserviceapp.data.manager.userServiceData

import android.util.Base64
import android.util.Log
import com.example.sellingserviceapp.data.local.repository.service.IServiceRepository
import com.example.sellingserviceapp.data.network.offer.repository.OfferRepository
import com.example.sellingserviceapp.model.domain.NewServiceDomain
import com.example.sellingserviceapp.model.domain.ServiceDomain
import com.example.sellingserviceapp.model.dto.ServiceDto
import com.example.sellingserviceapp.model.mapper.NewServiceMapper.toDto
import com.example.sellingserviceapp.model.mapper.ServiceConverters.toEntity
import com.example.sellingserviceapp.model.mapper.serviceDtoListToEntityList
import com.example.sellingserviceapp.model.mapper.serviceEntityFlowToDomainFlow
import com.example.sellingserviceapp.model.mapper.serviceEntityListToDomainList
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class UserServiceDataRepository @Inject constructor(
    private val offerRepository: OfferRepository,
    private val serviceRepository: IServiceRepository
) : IUserServiceDataRepository {
    override fun getServices(): Flow<List<ServiceDomain>> {
        return serviceEntityListToDomainList(serviceRepository.getServices())
    }

    override suspend fun updateServiceImage(serviceId: Int, imageBase64: String) {
        val decodedBytes = Base64.decode(imageBase64, Base64.DEFAULT)
        val requestBody = decodedBytes.toRequestBody("image/jpg".toMediaTypeOrNull())
        val file = MultipartBody.Part.createFormData("multipartFile", "serviceImage.jpg", requestBody)
        offerRepository.updateServiceImage(serviceId, file).onSuccess { success ->
            serviceRepository.updateServiceImage(image = imageBase64, imagePath = success.photoPath, serviceId = serviceId)
        }
    }

    override suspend fun requestService(serviceId: Int): ServiceDto {
        return offerRepository.getService(serviceId).getOrElse { ServiceDto.EMPTY }
    }

    override suspend fun fetchUserServices(page: Int, size: Int) {
        val fetchServicesDto = offerRepository.searchUserServices(page, size)
        fetchServicesDto.onSuccess {
            Log.d("FETCH_USER_SERVICES_DATA_MANAGER", it.toString())
            insertAllServices(it)
        }
    }

    override suspend fun updateService(serviceId: Int, service: NewServiceDomain) {
        val updateServiceRequest = offerRepository.updateService(service.toDto(), serviceId)
        updateServiceRequest.onSuccess {
            updateService(serviceId)
        }
    }

    override suspend fun getService(serviceId: Int): Flow<ServiceDomain> {
        //updateService(serviceId)
        return serviceEntityFlowToDomainFlow(serviceRepository.getService(serviceId))
    }

    override suspend fun getServiceImage(photoPath: String): String? {
        return offerRepository.getServiceImage(photoPath).getOrNull()
    }

    override suspend fun updateService(serviceId: Int) {
        val getServiceResponse = offerRepository.getService(serviceId)
        getServiceResponse.onSuccess { serviceDto ->
            val photo = getServiceImage(serviceDto.photoPath?: "")
            Log.d("UPDATE_SERVICE", serviceDto.formats.toString())
            serviceRepository.updateService(serviceDto.toEntity(photo))
        }
    }

    override suspend fun createService(newService: NewServiceDomain) {
        val createServiceResponse = offerRepository.createServiceRequest(newService.toDto())
        createServiceResponse.onSuccess {
            val newServiceResponse = requestService(it)
            serviceRepository.insertService(newServiceResponse.toEntity())
        }
    }

    override suspend fun deleteService(serviceId: Int) {
        val deleteServiceResponse = offerRepository.deleteService(serviceId)
        deleteServiceResponse.onSuccess {
            updateService(serviceId)
        }
    }

    override suspend fun insertAllServices(servicesDto: List<ServiceDto>) {
        val servicesEntity = serviceDtoListToEntityList(servicesDto)
        serviceRepository.insertAllService(servicesEntity)
    }
}