package com.example.sellingserviceapp.ui.screen.main.service

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.manager.DataManager
import com.example.sellingserviceapp.data.network.feedback.model.FeedbackDto
import com.example.sellingserviceapp.model.FeedbackWithData
import com.example.sellingserviceapp.model.domain.ServiceDomain
import com.example.sellingserviceapp.model.domain.UserDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor(
    private val dataManager: DataManager,
): ViewModel() {

    var serviceSheetContentState by mutableStateOf<ServiceSheetContentState>(ServiceSheetContentState.Booking)
    var service by mutableStateOf<ServiceDomain>(ServiceDomain.EMPTY)
    var user by mutableStateOf<UserDomain>(UserDomain.EMPTY)
    var serviceUIState by mutableStateOf<ServiceUIState>(ServiceUIState.Init)

    var feedbackList by mutableStateOf<List<FeedbackWithData>>(emptyList())
    var rating by mutableDoubleStateOf(0.0)

    @RequiresApi(Build.VERSION_CODES.O)
    fun init(serviceId: Int) {
        serviceUIState = ServiceUIState.Init
        viewModelScope.launch {
            val serviceJob = async { dataManager.requestMainService(serviceId) }
            service = serviceJob.await()
            val userJob = async { dataManager.fetchUserById(service.userId) }
            user = userJob.await()
            val feedbackJob = async { dataManager.getFeedbackWithDataForService(service.id, page = 0, size = 20) }
            feedbackList = feedbackJob.await()
            Log.d("SERVICE_VIEW_MODEL_RATING", serviceId.toString())
            val ratingJob = async { dataManager.getFeedbackRating(serviceId) }
            rating = ratingJob.await()
            serviceUIState = ServiceUIState.Loaded
        }
    }
}