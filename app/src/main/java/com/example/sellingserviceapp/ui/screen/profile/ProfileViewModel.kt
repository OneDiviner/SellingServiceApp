package com.example.sellingserviceapp.ui.screen.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.manager.DataManager
import com.example.sellingserviceapp.data.network.booking.IBookingRepository
import com.example.sellingserviceapp.data.network.booking.TimeTable
import com.example.sellingserviceapp.model.domain.UserDomain
import com.example.sellingserviceapp.model.mapper.daysListCodeToName
import com.google.gson.annotations.SerializedName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NewWorkTime(
    @SerializedName("start_time") val startTime: String,
    @SerializedName("end_time") val endTime: String,
    @SerializedName("days") val daysIds: List<Long>,
) {
    companion object {
        val EMPTY = NewWorkTime(
            startTime = "5",
            endTime = "5",
            daysIds = emptyList()
        )
    }
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataManager: DataManager,
    private val bookingRepository: IBookingRepository
): ViewModel() {

    var profileSheetState by mutableStateOf<ProfileSheetState>(ProfileSheetState.EditProfile)

    private val _userFlow = MutableStateFlow<UserDomain>(UserDomain.EMPTY)
    val userFLow: StateFlow<UserDomain> = _userFlow.asStateFlow()

    private val _timeTableFlow = MutableStateFlow<TimeTable?>(TimeTable(0, 0, "","", "", "", emptyList()))
    val timeTable: StateFlow<TimeTable?> = _timeTableFlow.asStateFlow()

    var newWorkTime by mutableStateOf<NewWorkTime>(NewWorkTime.EMPTY)


    init {
        getUser()
        getTimeTable()
    }

    fun getUser() {
        viewModelScope.launch {
            dataManager.getUserFlow().collect { user ->
                _userFlow.value = user
            }
        }
    }

    fun getTimeTable() {
        viewModelScope.launch {
            _timeTableFlow.value = bookingRepository.getUserTimeTable().getOrElse { TimeTable(0, 0, "","", "", "", emptyList()) }
            newWorkTime = newWorkTime.copy(
                startTime = _timeTableFlow.value?.startTime ?: "",
                endTime = _timeTableFlow.value?.endTime ?: "",
                daysIds = _timeTableFlow.value?.days?.map { it.id } ?: emptyList()
            )
            val daysCodeToName =  daysListCodeToName(_timeTableFlow.value?.days ?: emptyList())
            val startTime = _timeTableFlow.value?.startTime
            val endTime = _timeTableFlow.value?.endTime
            if(startTime != "" && endTime != "") {
                _timeTableFlow.value = _timeTableFlow.value?.copy(
                    days = daysCodeToName.sortedBy { it.id },
                    startTime = startTime?.substring(0, 5) ?: "",
                    endTime = endTime?.substring(0, 5) ?: ""
                )
            }
        }
    }

    fun updateWorkTime() {
        viewModelScope.launch {
           val newWorkTimeResponse = bookingRepository.updateWorkTime(newWorkTime)
            newWorkTimeResponse.onSuccess {
                _timeTableFlow.value = it
                newWorkTime = newWorkTime.copy(
                    startTime = _timeTableFlow.value?.startTime ?: "",
                    endTime = _timeTableFlow.value?.endTime ?: "",
                    daysIds = _timeTableFlow.value?.days?.map { it.id } ?: emptyList()
                )
                val daysCodeToName =  daysListCodeToName(_timeTableFlow.value?.days ?: emptyList())
                val startTime = _timeTableFlow.value?.startTime?.substring(0, 5) ?: ""
                val endTime = _timeTableFlow.value?.endTime?.substring(0, 5) ?: ""
                _timeTableFlow.value = _timeTableFlow.value?.copy(
                    days = daysCodeToName.sortedBy { it.id },
                    startTime = startTime,
                    endTime = endTime
                )
            }
        }
    }

    fun inputStartTime(hour: Int, minute: Int) {
        var textHour = hour.toString()
        var textMinute = minute.toString()
        if (textHour.length == 1) textHour = "0$textHour"
        if (textMinute.length == 1) textMinute = "0$textMinute"
        _timeTableFlow.value = _timeTableFlow.value?.copy(
            startTime = "$textHour:$textMinute"
        )
        newWorkTime = newWorkTime.copy(
            startTime = "$textHour:$textMinute:00"
        )
    }

    fun inputEndTime(hour: Int, minute: Int) {
        var textHour = hour.toString()
        var textMinute = minute.toString()
        if (textHour.length == 1) textHour = "0$textHour"
        if (textMinute.length == 1) textMinute = "0$textMinute"
        _timeTableFlow.value = _timeTableFlow.value?.copy(
            endTime = "$textHour:$textMinute"
        )
        newWorkTime = newWorkTime.copy(
            endTime = "$textHour:$textMinute:00"
        )
    }

    fun onPhotoSelected(base64: String?) {
        viewModelScope.launch {
            dataManager.updateAvatar(base64?: "")
        }
    }



    fun logout() {
        viewModelScope.launch {
            dataManager.logout()
        }
    }

}