package com.example.sellingserviceapp.data.di

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

sealed class AppState {
    object AuthState: AppState()
    object MainAppState: AppState()
}

class GlobalAppState @Inject constructor(
) {
    var appState by mutableStateOf<AppState>(AppState.AuthState)
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideGlobalAppState(): GlobalAppState {
        return GlobalAppState()
    }
}