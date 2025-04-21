package com.example.sellingserviceapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.sellingserviceapp.data.di.AppState
import com.example.sellingserviceapp.data.di.GlobalAppState
import com.example.sellingserviceapp.data.di.SecureTokenStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Objects
import javax.inject.Inject



//TODO: Сделать интерфейс для доступа к состоянию всего приложения

@HiltViewModel
class SellingServiceAppViewModel @Inject constructor(
    private val globalAppState: GlobalAppState,
    private val secureTokenStorage: SecureTokenStorage
): ViewModel() {


    
    var appState by mutableStateOf<AppState>(AppState.AuthState)
        private set

    fun refreshState() {
        appState = globalAppState.appState
    }

}