package com.example.sellingserviceapp.ui.screen.createService.userService

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.manager.DataManager
import com.example.sellingserviceapp.data.network.gpt.repository.GPTRepository
import com.example.sellingserviceapp.data.network.gpt.request.GenerateImageForServiceRequest
import com.example.sellingserviceapp.model.domain.CategoryDomain
import com.example.sellingserviceapp.model.domain.FormatsDomain
import com.example.sellingserviceapp.model.domain.PriceTypeDomain
import com.example.sellingserviceapp.model.domain.ServiceDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserServiceViewModel @Inject constructor(
    private val dataManager: DataManager,
    private val gptRepository: GPTRepository
): ViewModel() {

    var serviceState by mutableStateOf<ServiceState>(ServiceState.Service)

    private val _serviceFlow = MutableStateFlow<ServiceDomain>(ServiceDomain.EMPTY)
    val serviceFlow: StateFlow<ServiceDomain> = _serviceFlow.asStateFlow()

    var categories by mutableStateOf<List<CategoryDomain>>(emptyList())
    var formats by mutableStateOf<List<FormatsDomain>>(emptyList())
    var priceTypes by mutableStateOf<List<PriceTypeDomain>>(emptyList())

    init {
        serviceState = ServiceState.Loading
        viewModelScope.launch {
            val categoriesJob = async { dataManager.getCategories() }
            val formatsJob = async { dataManager.getFormats() }
            val priceTypesJob = async { dataManager.getPriceTypes() }

            categories = categoriesJob.await()
            formats = formatsJob.await()
            priceTypes = priceTypesJob.await()
            serviceState = ServiceState.Service
        }
    }

    fun updateServiceDomain(newServiceDomain: ServiceDomain) {
        _serviceFlow.value = newServiceDomain
        // Здесь можно добавить дополнительную логику, если нужно,
        // например, изменить serviceState в зависимости от newServiceDomain
        if (newServiceDomain == ServiceDomain.EMPTY && serviceState != ServiceState.Loading) { // Пример условия
            // Возможно, какая-то логика обработки пустого состояния
        } else if (newServiceDomain != ServiceDomain.EMPTY && serviceState == ServiceState.Loading) {
            serviceState = ServiceState.Service // Если пришли данные, переходим в состояние Service
        }
    }

    fun deleteService() {
        viewModelScope.launch {
            dataManager.deleteService(_serviceFlow.value.id)
        }
    }

    fun onPhotoSelected(serviceId: Int, imageBase64: String?) {
        viewModelScope.launch {
            dataManager.updateServiceImage(serviceId, imageBase64?: "")
        }
    }

    fun generateImage() {
        viewModelScope.launch {
            val result = gptRepository.generateImageForService(
                request = GenerateImageForServiceRequest(
                    category = _serviceFlow.value.categoryName,
                    subcategory = _serviceFlow.value.subcategoryName,
                    title = _serviceFlow.value.tittle,
                    description = _serviceFlow.value.description ?: ""
                )
            )
            result.onSuccess {
                val imageId = gptRepository.getGeneratedImage(it.fileId)
                imageId.onSuccess {
                    dataManager.updateServiceImage(
                        serviceId = _serviceFlow.value.id,
                        it
                    )
                }
            }
        }
    }
}