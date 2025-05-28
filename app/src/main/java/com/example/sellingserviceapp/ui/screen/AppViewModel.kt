package com.example.sellingserviceapp.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.DataManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
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
        initUserWithUserServices()
        initCategories()
        initFormats()
        initPriceTypes()
    }

    private fun initUserWithUserServices() {
        viewModelScope.launch {
            val userJob = async { dataManager.fetchUser() }
            userJob.await()
            val serviceJob = async { dataManager.fetchUserServices(PAGE, SIZE) }
            serviceJob.await()
        }
    }

    private fun initCategories() {
        viewModelScope.launch {
            dataManager.fetchCategories()
        }
    }
    private fun initFormats() {
        viewModelScope.launch {
            dataManager.fetchFormats()
        }
    }
    private fun initPriceTypes() {
        viewModelScope.launch {
            dataManager.fetchPriceTypes()
        }
    }
}