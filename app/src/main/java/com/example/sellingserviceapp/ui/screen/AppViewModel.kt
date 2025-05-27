package com.example.sellingserviceapp.ui.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.DataManager

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val dataManager: DataManager
): ViewModel() {

    companion object {
        private const val PAGE = 0
        const val SIZE = 20
    }

    var appSheetContentState by mutableStateOf<AppSheetContentState>(AppSheetContentState.Profile)

    init {
        initUser()
    }

    private fun initUser() {
        viewModelScope.launch {
            dataManager.fetchUser()
        }
    }

}