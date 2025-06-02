package com.example.sellingserviceapp.ui.screen.main.service.booking.confirmBooking

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.network.booking.BookingRepository
import com.example.sellingserviceapp.data.network.booking.IBookingRepository
import com.example.sellingserviceapp.model.domain.ServiceDomain
import com.example.sellingserviceapp.model.domain.UserDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmBookingViewModel @Inject constructor(
    private val bookingRepository: IBookingRepository
): ViewModel() {
    private val _serviceFlow = MutableStateFlow<ServiceDomain>(ServiceDomain.EMPTY)
    val serviceFlow: StateFlow<ServiceDomain> = _serviceFlow.asStateFlow()

    private val _userFlow = MutableStateFlow<UserDomain>(UserDomain.EMPTY)
    val userFlow: StateFlow<UserDomain> = _userFlow.asStateFlow()

    private val _dateFlow = MutableStateFlow<String>("")
    val dateFlow: StateFlow<String> = _dateFlow.asStateFlow()

    private val _timeFlow = MutableStateFlow<String>("")
    val timeFlow: StateFlow<String> = _timeFlow.asStateFlow()

    fun initData(service: ServiceDomain, user: UserDomain, date: String, time: String) {
        _serviceFlow.value = service
        _userFlow.value = user
        _dateFlow.value = date
        _timeFlow.value = time
    }
    fun createBooking() {
        viewModelScope.launch {
            val date = "${_dateFlow.value} ${_timeFlow.value}"
            val response = bookingRepository.createBooking(_serviceFlow.value.id, date)
            response.onSuccess {
                Log.d("DATE", it.toString())
            }
        }
    }
}