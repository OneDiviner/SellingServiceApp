package com.example.sellingserviceapp.ui.screen.main.service

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.DataManager
import com.example.sellingserviceapp.model.domain.ServiceDomain
import com.example.sellingserviceapp.model.domain.UserDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor(
    private val dataManager: DataManager
): ViewModel() {

    var serviceSheetContentState by mutableStateOf<ServiceSheetContentState>(ServiceSheetContentState.Booking)
    var service by mutableStateOf<ServiceDomain>(ServiceDomain.EMPTY)
    var user by mutableStateOf<UserDomain>(UserDomain.EMPTY)
    var isLoading by mutableStateOf(true)

    fun init(serviceId: Int) {
        isLoading = true
        viewModelScope.launch {
            val serviceJob = async { dataManager.requestMainService(serviceId) }
            service = serviceJob.await()
            val userJob = async { dataManager.fetchUserById(service.userId) }
            user = userJob.await()
            isLoading = false
        }
    }
}