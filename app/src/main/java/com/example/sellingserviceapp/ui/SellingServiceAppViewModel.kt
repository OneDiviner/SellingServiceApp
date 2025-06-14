package com.example.sellingserviceapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.DataManager
import com.example.sellingserviceapp.data.di.GlobalAppState
import com.example.sellingserviceapp.data.di.SecureTokenStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject



//TODO: Сделать интерфейс для доступа к состоянию всего приложения

@HiltViewModel
class SellingServiceAppViewModel @Inject constructor(
    val globalAppState: GlobalAppState,
    private val dataManager: DataManager,
    private val secureTokenStorage: SecureTokenStorage
): ViewModel() {

    companion object {
        private const val PAGE = 0
        const val SIZE = 20
    }

    init {
        viewModelScope.launch {
            if (secureTokenStorage.hasTokens()) {
                Log.d("INIT_SELLINGSERVICE_APP", "HAS_TOKENS")
                globalAppState.setMainAppState()
            } else {
                globalAppState.setAuthState()
            }
        }
    }

    fun initUserWithUserServices() {
        viewModelScope.launch {
            val userJob = async { dataManager.fetchUser() }
            userJob.await()
            val serviceJob = async { dataManager.fetchUserServices(PAGE, SIZE) }
            serviceJob.await()
        }
    }

   fun initCategories() {
        viewModelScope.launch {
            dataManager.fetchCategories()
        }
    }
    fun initFormats() {
        viewModelScope.launch {
            dataManager.fetchFormats()
        }
    }
    fun initPriceTypes() {
        viewModelScope.launch {
            dataManager.fetchPriceTypes()
        }
    }
}