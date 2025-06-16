package com.example.sellingserviceapp.data.di

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

sealed class AppState {
    object AuthState: AppState()
    object MainAppState: AppState()
    object LoadingState: AppState()
}

interface IGlobalAppState {
    var appState: AppState
    fun setAuthState()
    fun setMainAppState()
    fun setLoadingState()
}

class GlobalAppState @Inject constructor(
) {
    //var appState by mutableStateOf<AppState>(AppState.LoadingState)

    private val _appState = MutableStateFlow<AppState>(AppState.LoadingState)
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    fun setAuthState() {
        _appState.value = AppState.AuthState
    }

    fun setLoadingState() {
        _appState.value = AppState.LoadingState
    }

    fun setMainAppState() {
        _appState.value = AppState.MainAppState
    }

}

@Module
@InstallIn(SingletonComponent::class)
object GlobalAppStateModule {
    @Provides
    @Singleton
    fun provideGlobalAppState(): GlobalAppState {
        return GlobalAppState()
    }
}