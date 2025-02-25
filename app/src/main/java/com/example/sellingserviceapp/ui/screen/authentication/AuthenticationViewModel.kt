package com.example.sellingserviceapp.ui.screen.authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AuthenticationViewModel: ViewModel() {

    var authenticationState by mutableStateOf<AuthenticationState>(AuthenticationState.Idle)
        private set

}