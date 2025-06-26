package com.example.sellingserviceapp.ui.screen.feedback

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.manager.DataManager
import com.example.sellingserviceapp.data.network.feedback.model.AvailableFeedbacks
import com.example.sellingserviceapp.model.FeedbackWithData
import com.example.sellingserviceapp.model.domain.BookingWithData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class FeedbackViewModel @Inject constructor(
    private val dataManager: DataManager
) : ViewModel() {

    var feedbackUIState by mutableStateOf<FeedbackUIState>(FeedbackUIState.Init)

    var isLoading by mutableStateOf(false)

    var isSheetOpen by mutableStateOf(false)

    var feedbackSheetState by mutableStateOf<FeedbackSheetState>(FeedbackSheetState.New)

    var pickedFeedback by mutableStateOf<FeedbackWithData>(FeedbackWithData.EMPTY)

    private val _availableFeedbackFlow = MutableStateFlow<List<FeedbackWithData>>(emptyList())
    val availableFeedbackFlow: StateFlow<List<FeedbackWithData>> = _availableFeedbackFlow.asStateFlow()

    private val _feedbackFlow = MutableStateFlow<List<FeedbackWithData>>(emptyList())
    val feedbackFlow: StateFlow<List<FeedbackWithData>> = _feedbackFlow.asStateFlow()

    init {
        init()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun init() {
        feedbackUIState = FeedbackUIState.Init
        viewModelScope.launch {
            _availableFeedbackFlow.value = dataManager.getAvailableFeedbackWithData(0, 20)
            _feedbackFlow.value = dataManager.getUserFeedbackWithData(0, 20)
            feedbackUIState = FeedbackUIState.Loaded
        }
    }

    fun createFeedback(comment: String, rating: Int) {
        viewModelScope.launch {
            dataManager.createFeedbackForBooking(pickedFeedback.feedback.bookingId, comment, rating)
            val updateListJob = async { updateList() }
            updateListJob.await()
        }
    }

    fun updateList() {
        isLoading = true
        viewModelScope.launch {
            _availableFeedbackFlow.value = dataManager.getAvailableFeedbackWithData(0, 20)
            _feedbackFlow.value = dataManager.getUserFeedbackWithData(0, 20)
            isLoading = false
        }
    }

    fun editFeedback(comment: String, rating: Int) {
        viewModelScope.launch {
            dataManager.updateFeedback(pickedFeedback.feedback.id, comment, rating)
            val updateListJob = async { updateList() }
            updateListJob.await()
        }
    }

}