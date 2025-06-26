package com.example.sellingserviceapp.ui.screen.offer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.manager.DataManager
import com.example.sellingserviceapp.data.network.booking.Status
import com.example.sellingserviceapp.model.domain.BookingWithData
import com.example.sellingserviceapp.model.mapper.BookingStatusAsClientMapper
import com.example.sellingserviceapp.model.mapper.mapStatusListAsClient
import com.example.sellingserviceapp.ui.screen.order.DialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OffersViewModel @Inject constructor(
    private val dataManager: DataManager
): ViewModel() {

    var isFilterSelected by mutableStateOf<Int?>(null)

    private val _bookingsAsClientFlow = MutableStateFlow<List<BookingWithData>>(emptyList())
    val bookingsAsClientFlow: StateFlow<List<BookingWithData>> = _bookingsAsClientFlow.asStateFlow()

    var isBookingPicked by mutableStateOf(false)

    var pickedBooking by mutableStateOf<BookingWithData>(BookingWithData.EMPTY)
    var pickedBookingDialogState by mutableStateOf<DialogState>(DialogState.NewBooking)

    private val _statusListFlow = MutableStateFlow<List<Status>>(emptyList())
    val statusListFlow: StateFlow<List<Status>> = _statusListFlow.asStateFlow()

    var offerState by mutableStateOf<BookingState>(BookingState.Init)

    var isLoading by mutableStateOf(false)

    init {
        init()
    }

    fun getBookingAsClient() {
        isLoading = true
        viewModelScope.launch {
            _bookingsAsClientFlow.value = dataManager.getBookingAsClient(0, 20, isFilterSelected)
            isLoading = false
        }
    }

    fun init() {
        offerState = BookingState.Init
        viewModelScope.launch {
            _statusListFlow.value = mapStatusListAsClient(dataManager.getBookingStatuses())
            _bookingsAsClientFlow.value = dataManager.getBookingAsClient(0, 20, isFilterSelected)
            offerState = BookingState.Loaded
        }
    }



    fun createFeedback(bookingId: Int, rating: Int, comment: String) {
        viewModelScope.launch {
            dataManager.createFeedbackForBooking(bookingId, comment, rating)
        }
    }
}