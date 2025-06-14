package com.example.sellingserviceapp.ui.screen.offer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.DataManager
import com.example.sellingserviceapp.data.network.booking.Status
import com.example.sellingserviceapp.model.domain.BookingWithData
import com.example.sellingserviceapp.model.mapper.mapStatusListByClient
import com.example.sellingserviceapp.model.mapper.mapStatusListByExecutor
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
    var isRefreshing by mutableStateOf(false)

    var isFilterSelected by mutableStateOf<Int?>(null)

    private val _bookingsAsClientFlow = MutableStateFlow<List<BookingWithData>>(emptyList())
    val bookingsAsClientFlow: StateFlow<List<BookingWithData>> = _bookingsAsClientFlow.asStateFlow()

    var isBookingPicked by mutableStateOf(false)

    var pickedBooking by mutableStateOf<BookingWithData>(BookingWithData.EMPTY)
    var pickedBookingDialogState by mutableStateOf<DialogState>(DialogState.NewBooking)

    private val _statusListFlow = MutableStateFlow<List<Status>>(emptyList())
    val statusListFlow: StateFlow<List<Status>> = _statusListFlow.asStateFlow()

    init {
        getBookingAsClient()
        getStatuses()
    }

    fun getBookingAsClient(statusId: Int? = null) {
        viewModelScope.launch {
            viewModelScope.launch {
                if (isFilterSelected != null) {
                    _bookingsAsClientFlow.value = dataManager.getBookingAsClient(0, 20, statusId)
                } else {
                    _bookingsAsClientFlow.value = dataManager.getBookingAsClient(0, 20)
                }
            }

        }
    }

    fun getStatuses() {
        viewModelScope.launch {
            _statusListFlow.value = mapStatusListByClient(dataManager.getBookingStatuses())
        }
    }
}