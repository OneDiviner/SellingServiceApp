package com.example.sellingserviceapp.ui.screen.createService.service

import android.util.Base64
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.DataManager
import com.example.sellingserviceapp.model.domain.CategoryDomain
import com.example.sellingserviceapp.model.domain.FormatsDomain
import com.example.sellingserviceapp.model.domain.NewServiceDomain
import com.example.sellingserviceapp.model.domain.PriceTypeDomain
import com.example.sellingserviceapp.model.domain.ServiceDomain
import com.example.sellingserviceapp.model.domain.SubcategoryDomain
import com.example.sellingserviceapp.model.domain.UserDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor(
    private val dataManager: DataManager
): ViewModel() {

    var serviceState by mutableStateOf<ServiceState>(ServiceState.Service)

    var service by mutableStateOf<ServiceDomain>(ServiceDomain.EMPTY)

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

    fun onPhotoSelected(serviceId: Int, imageBase64: String?) {
        viewModelScope.launch {
            dataManager.updateServiceImage(serviceId, imageBase64?: "")
        }
    }
}