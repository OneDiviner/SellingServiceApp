package com.example.sellingserviceapp.ui


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.data.di.AppState
import com.example.sellingserviceapp.ui.component.circularProgressIndicator.FullScreenCircularProgressIndicator
import com.example.sellingserviceapp.ui.screen.authentication.AuthenticationSellingServiceApp
import com.example.sellingserviceapp.ui.screen.AppUI


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
    StatusBarProtection()
}

@Composable
private fun StatusBarProtection(
    color: Color = MaterialTheme.colorScheme.surfaceContainer,
    heightProvider: () -> Float = calculateGradientHeight(),
) {
    Canvas(Modifier.fillMaxSize()) {
        val calculatedHeight = heightProvider()
        val gradient = Brush.verticalGradient(
            colors = listOf(
                color.copy(alpha = 0.6f),
                color.copy(alpha = 0.2f),
                Color.Transparent
            ),
            startY = 0f,
            endY = calculatedHeight
        )
        drawRect(
            brush = gradient,
            size = Size(size.width, calculatedHeight),
        )
    }
}

@Composable
fun calculateGradientHeight(): () -> Float {
    val statusBars = WindowInsets.statusBars
    val density = LocalDensity.current
    return { statusBars.getTop(density).times(1.2f) }
}
