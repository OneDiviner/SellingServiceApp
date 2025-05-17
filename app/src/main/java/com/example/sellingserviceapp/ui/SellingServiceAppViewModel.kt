package com.example.sellingserviceapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.DataManager
import com.example.sellingserviceapp.data.di.GlobalAppState
import com.example.sellingserviceapp.data.di.SecureTokenStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject



//TODO: Сделать интерфейс для доступа к состоянию всего приложения

@HiltViewModel
class SellingServiceAppViewModel @Inject constructor(
    val globalAppState: GlobalAppState,
    private val dataManager: DataManager,
    private val secureTokenStorage: SecureTokenStorage
): ViewModel() {

    init {
        viewModelScope.launch {
            if (secureTokenStorage.hasTokens()) {
                globalAppState.setMainAppState()
            } else {
                globalAppState.setAuthState()
            }
        }
    }
}