package com.example.sellingserviceapp.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.di.AppState
import com.example.sellingserviceapp.data.di.GlobalAppState
import com.example.sellingserviceapp.data.di.SecureTokenStorage
import com.example.sellingserviceapp.data.local.UserDataStorage
import com.example.sellingserviceapp.data.model.AuthApiError
import com.example.sellingserviceapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.Objects
import javax.inject.Inject



//TODO: Сделать интерфейс для доступа к состоянию всего приложения

@HiltViewModel
class SellingServiceAppViewModel @Inject constructor(
    val globalAppState: GlobalAppState,
    private val secureTokenStorage: SecureTokenStorage,
    private val userDataStorage: UserDataStorage,
    private val authRepository: AuthRepository
): ViewModel() {

    //TODO: Разобраться с глобальным состоянием с хранилищем данных

    init {
        viewModelScope.launch {
            userDataStorage.updateUserData()
            if (secureTokenStorage.hasTokens()) {
                globalAppState.setMainAppState()
            } else {
                globalAppState.setAuthState()
            }
        }
    }
}