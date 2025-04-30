package com.example.sellingserviceapp.ui


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.data.di.AppState
import com.example.sellingserviceapp.ui.component.circularProgressIndicator.FullScreenCircularProgressIndicator
import com.example.sellingserviceapp.ui.screen.authentication.AuthenticationSellingServiceApp
import com.example.sellingserviceapp.ui.screen.profile.AppUI


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellingServiceApp(
    viewModel: SellingServiceAppViewModel = hiltViewModel(),
) {

    val state by viewModel.globalAppState.appState.collectAsState()

    when(state) {
        is AppState.LoadingState -> {
            FullScreenCircularProgressIndicator()
        }
        is AppState.AuthState -> {
            AuthenticationSellingServiceApp()
        }
        is AppState.MainAppState -> {
            AppUI()
        }
    }


}
