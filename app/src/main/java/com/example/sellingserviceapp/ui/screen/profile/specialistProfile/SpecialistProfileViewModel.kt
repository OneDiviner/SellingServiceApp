package com.example.sellingserviceapp.ui.screen.profile.specialistProfile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.repository.AuthRepository
import com.example.sellingserviceapp.ui.screen.profile.specialistProfile1.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class SpecialistProfileViewModel@Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

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