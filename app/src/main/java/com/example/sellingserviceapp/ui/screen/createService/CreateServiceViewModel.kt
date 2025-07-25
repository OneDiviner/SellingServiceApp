package com.example.sellingserviceapp.ui.screen.createService

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.manager.DataManager
import com.example.sellingserviceapp.data.network.offer.repository.OfferRepository
import com.example.sellingserviceapp.model.domain.ServiceDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateServiceViewModel @Inject constructor(
    private val dataManager: DataManager
): ViewModel() {

    val serviceListFlow = MutableStateFlow<List<ServiceDomain>>(emptyList())

    var isOpen by mutableStateOf(false)

    var sheetContentState by mutableStateOf<SheetContentState>(SheetContentState.NewService)

    init {
        observeServiceList()
    }

    fun observeServiceList() {
        viewModelScope.launch {
            dataManager.getServices().collect { services ->
                serviceListFlow.value = services
            }
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