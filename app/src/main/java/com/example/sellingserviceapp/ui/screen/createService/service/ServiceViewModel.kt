package com.example.sellingserviceapp.ui.screen.createService.service

import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.DataManager
import com.example.sellingserviceapp.model.domain.ServiceDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
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

    private val serviceIdFlow = MutableStateFlow<Int?>(null)
    @OptIn(ExperimentalCoroutinesApi::class)
    val serviceFlow: StateFlow<ServiceDomain?> = serviceIdFlow
        .filterNotNull()
        .flatMapLatest { id ->
            dataManager.getService(id)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ServiceDomain.EMPTY
        )

    init {

    }

    fun getServiceById(serviceId: Int) {
        serviceIdFlow.value = serviceId
    }

    fun onPhotoSelected(serviceId: Int, imageBase64: String?) {
        viewModelScope.launch {
            dataManager.updateServiceImage(serviceId, imageBase64?: "")
        }
    }
}