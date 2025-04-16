package com.example.sellingserviceapp.ui.screen.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.ui.screen.settings.specialistProfile1.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ProfileSheetStateData {
    object Default: ProfileSheetStateData()
    object SpecialistState: ProfileSheetStateData()
    object ClientState: ProfileSheetStateData()
}


//TODO: Вынести асинхронные запросы в Repository
@HiltViewModel
class SettingsViewModel @Inject constructor(

): ViewModel() {

    var profileSheetState by mutableStateOf(false)
    var profileSheetStateData by mutableStateOf<ProfileSheetStateData>(ProfileSheetStateData.Default)

    var screenState by mutableStateOf<ScreenState>(ScreenState.Loading)
        private set

    init {
        viewModelScope.launch {
            screenState = ScreenState.Loading
            delay(2000L)
            screenState = ScreenState.Success
        }
    }
}