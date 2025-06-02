package com.example.sellingserviceapp.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.ui.screen.createService.CreateServiceUI
import com.example.sellingserviceapp.ui.screen.main.MainUI
import com.example.sellingserviceapp.ui.screen.main.service.ServiceUI
import com.example.sellingserviceapp.ui.screen.profile.ProfileUI
import kotlinx.coroutines.launch

sealed class AppSheetContentState {
    data object Profile: AppSheetContentState()
    data object UserServices: AppSheetContentState()
    data object Service: AppSheetContentState()
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppUI(
    viewModel: AppViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()

    val bottomSheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Hidden, // Начинаем со скрытого состояния
        skipHiddenState = false
    )
    val scaffoldBottomSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )

    val blurRadius = when (bottomSheetState.currentValue) {
        SheetValue.Expanded -> 10.dp
        else -> 0.dp // Это включает SheetValue.PartiallyExpanded и SheetValue.Hidden
    }

    var serviceIdFromMain by remember { mutableIntStateOf(0) }

    BottomSheetScaffold(
        scaffoldState = scaffoldBottomSheetState,
        sheetDragHandle = {
            Box(modifier = Modifier) {
                BottomSheetDefaults.DragHandle(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .systemBarsPadding()
                        .align(Alignment.BottomCenter),
                    color = MaterialTheme.colorScheme.onBackground,
                    height = 6.dp,
                    width = 40.dp
                )
            }
        },
        sheetShadowElevation = 0.dp,
        sheetShape = RoundedCornerShape(20.dp),
        sheetContainerColor = Color.Transparent,
        sheetPeekHeight = 0.dp,
        modifier = Modifier
            .blur(radius = blurRadius),
        content = { //TODO: Не инитит MainUI
            MainUI(
                onProfileButtonClick = {
                    viewModel.appSheetContentState = AppSheetContentState.Profile
                    scope.launch {
                        bottomSheetState.expand()
                    }
                },
                onUserServicesButtonClick = {
                    viewModel.appSheetContentState = AppSheetContentState.UserServices
                    scope.launch {
                        bottomSheetState.expand()
                    }
                },
                onServiceButtonClick = { serviceId ->
                    serviceIdFromMain = serviceId
                    viewModel.appSheetContentState = AppSheetContentState.Service
                    scope.launch {
                        bottomSheetState.expand()
                    }
                }
            )
        },
        sheetContent = {
            AnimatedContent(
                targetState = viewModel.appSheetContentState,
                transitionSpec = {
                    fadeIn(tween(500)) togetherWith fadeOut(tween(500))
                }
            ) {
                when(it) {
                    is AppSheetContentState.Profile -> {
                        ProfileUI { viewModel.appSheetContentState = AppSheetContentState.UserServices }
                    }
                    is AppSheetContentState.UserServices -> {
                        CreateServiceUI { viewModel.appSheetContentState = AppSheetContentState.Profile }
                    }
                    is AppSheetContentState.Service -> {
                        ServiceUI(
                            serviceId = serviceIdFromMain
                        )
                    }
                }
            }
        }
    )
}