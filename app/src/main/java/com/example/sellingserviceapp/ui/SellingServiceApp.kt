package com.example.sellingserviceapp.ui


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.data.di.AppState
import com.example.sellingserviceapp.ui.screen.authentication.AuthenticationSellingServiceApp
import com.example.sellingserviceapp.ui.screen.settings.AppUI


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellingServiceApp(
    viewModel: SellingServiceAppViewModel = hiltViewModel(),
) {
    viewModel.refreshState()
    when(viewModel.appState) {
        is AppState.AuthState -> {
            AuthenticationSellingServiceApp()
        }
        is AppState.MainAppState -> {
            AppUI()
        }
    }


}
