package com.example.sellingserviceapp.ui.screen.createService

import android.util.Base64
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.DataManager
import com.example.sellingserviceapp.data.local.UserData
import com.example.sellingserviceapp.data.local.UserDataStorage
import com.example.sellingserviceapp.data.network.offer.response.ShortServiceData
import com.example.sellingserviceapp.data.network.offer.repository.OfferRepository
import com.example.sellingserviceapp.model.domain.ServiceDomain
import com.example.sellingserviceapp.model.dto.FormatsDto
import com.example.sellingserviceapp.ui.screen.createService.model.Category
import com.example.sellingserviceapp.ui.screen.createService.model.LocationType
import com.example.sellingserviceapp.ui.screen.createService.model.PriceType
import com.example.sellingserviceapp.ui.screen.createService.model.Service
import com.example.sellingserviceapp.ui.screen.createService.model.ShortService
import com.example.sellingserviceapp.ui.screen.createService.model.Subcategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class CreateServiceViewModel @Inject constructor(
    private val offerRepository: OfferRepository,
    private val dataManager: DataManager
): ViewModel() {

    //TODO: Сделать хранилище для услуг!!!!

    val serviceListFlow: StateFlow<List<ServiceDomain>> =
        dataManager.getServices()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    var isOpen by mutableStateOf(false)

    var sheetContentState by mutableStateOf<SheetContentState>(SheetContentState.NewService)

    var isRefreshing by mutableStateOf(false)

    init {
        viewModelScope.launch {
            dataManager.insertAllServices()
            dataManager.requestFormats()
            dataManager.requestPriceTypes()
            dataManager.requestCategories()
        }
    }

    fun getServiceById(serviceId: Int): Flow<ServiceDomain> {
        return flow {
            val serviceFlow = dataManager.getService(serviceId)
            serviceFlow.collect { service ->
                emit(service)
            }
        }
    }

    fun updateService(serviceId: Int) {
        viewModelScope.launch {
            dataManager.updateService(serviceId)
        }
    }
}