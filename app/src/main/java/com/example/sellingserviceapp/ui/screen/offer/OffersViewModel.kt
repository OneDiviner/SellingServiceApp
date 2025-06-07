package com.example.sellingserviceapp.ui.screen.offer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.DataManager
import com.example.sellingserviceapp.model.domain.BookingWithData
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

    private val _bookingsAsClientFlow = MutableStateFlow<List<BookingWithData>>(emptyList())
    val bookingsAsClientFlow: StateFlow<List<BookingWithData>> = _bookingsAsClientFlow.asStateFlow()

    init {
        getBookingAsClient()
    }

    fun getBookingAsClient() {
        viewModelScope.launch {
            _bookingsAsClientFlow.value = dataManager.getBookingAsClient(0, 20)
        }
    }
}