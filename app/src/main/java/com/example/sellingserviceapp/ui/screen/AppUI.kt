package com.example.sellingserviceapp.ui.screen

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.ui.screen.createService.CreateServiceUI
import com.example.sellingserviceapp.ui.screen.main.MainUI
import com.example.sellingserviceapp.ui.screen.main.service.ServiceUI
import com.example.sellingserviceapp.ui.screen.offer.OffersUI
import com.example.sellingserviceapp.ui.screen.order.OrdersUI
import com.example.sellingserviceapp.ui.screen.profile.ProfileUI
import kotlinx.coroutines.launch

sealed class AppSheetContentState {
    data object Profile: AppSheetContentState()
    data object UserServices: AppSheetContentState()
    data object Service: AppSheetContentState()
    data object Orders: AppSheetContentState()
    data object Offers: AppSheetContentState()
}

@Composable
fun CustomSnackbar(
    data: SnackbarData,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Surface(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 30.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surfaceContainer
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = data.visuals.message,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f)
                )

                data.visuals.actionLabel?.let { actionLabel ->
                    TextButton(
                        onClick = { data.performAction() },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(actionLabel)
                    }
                }
            }
        }
    }
}

@Composable
fun CustomSnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    snackbar: @Composable (SnackbarData) -> Unit = { data ->
        CustomSnackbar(data = data)
    }
) {
    SnackbarHost(
        hostState = hostState,
        snackbar = snackbar,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppUI(
    viewModel: AppViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

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


    val snackbarMessage by viewModel.errorMessage.collectAsState()

    // Автоматически показываем Snackbar при изменении сообщения
    LaunchedEffect(key1 = snackbarMessage) {
        snackbarMessage?.let { message ->
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
            // Уведомляем ViewModel, что Snackbar был показан
            viewModel.onSnackbarShown()
        }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldBottomSheetState,
        snackbarHost = {
            CustomSnackbarHost(hostState = snackbarHostState)
        },
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
        content = {
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
                },
                onOffersButtonClick = {
                    viewModel.appSheetContentState = AppSheetContentState.Offers
                    scope.launch {
                        bottomSheetState.expand()
                    }
                },
                onOrdersButtonClick = {
                    viewModel.appSheetContentState = AppSheetContentState.Orders
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
                    fadeIn(tween(300), initialAlpha = 0.8f) togetherWith fadeOut(tween(300), targetAlpha = 0.3f)
                }
            ) {
                when(it) {
                    is AppSheetContentState.Profile -> {
                        ProfileUI(
                            onMyServiceButtonClick = { viewModel.appSheetContentState = AppSheetContentState.UserServices },
                            onOrdersButtonClick = {  viewModel.appSheetContentState = AppSheetContentState.Orders },
                            onOffersButtonClick = { viewModel.appSheetContentState = AppSheetContentState.Offers }
                        )
                    }
                    is AppSheetContentState.UserServices -> {
                        CreateServiceUI { viewModel.appSheetContentState = AppSheetContentState.Profile }
                    }
                    is AppSheetContentState.Service -> {
                        ServiceUI(
                            serviceId = serviceIdFromMain
                        )
                    }
                    is AppSheetContentState.Orders -> {
                        OrdersUI { viewModel.appSheetContentState = AppSheetContentState.Profile }
                    }
                    is AppSheetContentState.Offers -> {
                        OffersUI { viewModel.appSheetContentState = AppSheetContentState.Profile }
                    }
                }
            }
        }
    )
}