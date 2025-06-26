package com.example.sellingserviceapp.ui.screen.order

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.manager.DataManager
import com.example.sellingserviceapp.data.network.booking.Status
import com.example.sellingserviceapp.model.domain.BookingWithData
import com.example.sellingserviceapp.model.mapper.mapStatusListAsExecutor
import com.example.sellingserviceapp.ui.screen.offer.BookingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val dataManager: DataManager
): ViewModel() {

    var orderState by mutableStateOf<BookingState>(BookingState.Init)

    var isLoading by mutableStateOf(false)

    var isBookingPicked by mutableStateOf(false)

    var pickedBooking by mutableStateOf<BookingWithData>(BookingWithData.EMPTY)
    var pickedBookingDialogState by mutableStateOf<DialogState>(DialogState.NewBooking)

    var isFilterSelected by mutableStateOf<Int?>(null)

    private val _bookingsAsExecutorFlow = MutableStateFlow<List<BookingWithData>>(emptyList())
    val bookingsAsExecutorFlow: StateFlow<List<BookingWithData>> = _bookingsAsExecutorFlow.asStateFlow()

    private val _statusListFlow = MutableStateFlow<List<Status>>(emptyList())
    val statusListFlow: StateFlow<List<Status>> = _statusListFlow.asStateFlow()

    init {
        init()
    }

    fun confirmBooking(bookingId: Int) {
        viewModelScope.launch {
            dataManager.confirmBookingAsExecutor(bookingId)
            _bookingsAsExecutorFlow.value = dataManager.getBookingAsExecutor(0, 20)
        }
    }

    fun rejectBooking(bookingId: Int) {
        viewModelScope.launch {
            dataManager.rejectBookingAsExecutor(bookingId)
            _bookingsAsExecutorFlow.value = dataManager.getBookingAsExecutor(0, 20)
        }
    }

    fun getBookingAsExecutor() {
        isLoading = true
        viewModelScope.launch {
            _bookingsAsExecutorFlow.value = dataManager.getBookingAsExecutor(0, 20, isFilterSelected)
            isLoading = false
        }
    }

    fun init() {
        orderState = BookingState.Init
        viewModelScope.launch {
            _statusListFlow.value = mapStatusListAsExecutor(dataManager.getBookingStatuses())
            _bookingsAsExecutorFlow.value = dataManager.getBookingAsExecutor(0, 20, isFilterSelected)
            orderState = BookingState.Loaded
        }
    }

}