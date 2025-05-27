package com.example.sellingserviceapp.ui.screen.main.service.booking

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.network.booking.BookingRepository
import com.example.sellingserviceapp.data.network.booking.Day
import com.example.sellingserviceapp.data.network.booking.IBookingRepository
import com.example.sellingserviceapp.model.domain.UserDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val bookingRepository: IBookingRepository
): ViewModel() {


    private val _slotsFlow = MutableStateFlow<List<String>>(emptyList())
    val slotsFLow: StateFlow<List<String>> = _slotsFlow.asStateFlow()

    private val _timeTableFlow = MutableStateFlow<List<Day>>(emptyList())
    val timeTableFlow: StateFlow<List<Day>> = _timeTableFlow.asStateFlow()

    var selectedTimeSlotState by mutableStateOf<String?>(null)
    var date by mutableStateOf<String?>(null)


    init {

    }

    fun getSlots(serviceId: Int, localDate: String) {
        viewModelScope.launch {
           val response =  bookingRepository.getAvailableWorkTime(serviceId, localDate)
            response.onSuccess {
                _slotsFlow.value = it
                Log.d("DATE", "2025-05-26")
            }.onFailure {
                Log.d("ERROR_IN_VIEW_MODEL", it.message.toString())
            }
        }
    }
    fun getTimeTable(userId: Int) {
        viewModelScope.launch {
            val response = bookingRepository.getTimeTable(userId)
            response.onSuccess {
                _timeTableFlow.value = it
                Log.d("TimeTable", it.toString())
            }.onFailure {
                Log.d("ERROR_IN_VIEW_MODEL", it.message.toString())
            }
        }
    }

    fun clearData() {
        _slotsFlow.value = emptyList()
    }
}