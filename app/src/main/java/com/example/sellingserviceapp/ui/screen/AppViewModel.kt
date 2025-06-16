package com.example.sellingserviceapp.ui.screen

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.manager.DataManager
import com.example.sellingserviceapp.data.network.ErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val dataManager: DataManager,
    private val errorHandler: ErrorHandler
): ViewModel() {

    companion object {
        private const val PAGE = 0
        const val SIZE = 20
    }

    var appSheetContentState by mutableStateOf<AppSheetContentState>(AppSheetContentState.Profile)

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        initUserWithUserServices()
        initCategories()
        initFormats()
        initPriceTypes()
        observeError()
    }

    private fun observeError() {
        viewModelScope.launch {
            errorHandler.observeError().collect { error ->
                _errorMessage.value = error
            }
        }
    }

    fun onSnackbarShown() {
        _errorMessage.value = null
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