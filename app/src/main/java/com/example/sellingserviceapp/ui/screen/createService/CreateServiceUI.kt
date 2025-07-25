package com.example.sellingserviceapp.ui.screen.createService

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.model.domain.ServiceDomain
import com.example.sellingserviceapp.ui.component.textfield.SearchTextField
import com.example.sellingserviceapp.ui.screen.createService.component.CategoryButton
import com.example.sellingserviceapp.ui.screen.createService.component.SheetStickyHeader
import com.example.sellingserviceapp.ui.screen.createService.newService.NewServiceUI
import com.example.sellingserviceapp.ui.screen.createService.userService.UserServiceUI
import com.example.sellingserviceapp.ui.screen.createService.userService.UserServiceViewModel
import kotlinx.coroutines.flow.Flow

sealed class CreateServiceUIState {
    object Loading: CreateServiceUIState()
    object Success: CreateServiceUIState()
}

sealed class SheetContentState {
    data object NewService: SheetContentState()
    data class Service(val serviceFlow: Flow<ServiceDomain>): SheetContentState()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateServiceUI(
    viewModel: CreateServiceViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit
) {

    LaunchedEffect(Unit) {

    }

    val services by viewModel.serviceListFlow.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
                .fillMaxSize()
                .displayCutoutPadding()
                .padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            // Строка поиска
            item {
                SearchTextField(
                    value = "",
                    onValueChanged = {}
                )
            }
            item {
                Row(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .padding(bottom = 15.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = onBackButtonClick,
                            modifier = Modifier
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = "Back",
                                modifier = Modifier
                                    .size(28.dp),
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        Text("Услуги", fontSize = 32.sp, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = {
                            viewModel.sheetContentState = SheetContentState.NewService
                            viewModel.isOpen = true
                        },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .width(150.dp)
                            .height(32.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize().padding(horizontal = 15.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                            Text("Новая услуга", fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground)
                        }

                    }
                }
            }
            items(services) { service ->
                if(service.statusCode != "STATUS_DELETED") {
                    CategoryButton(
                        category = service.tittle,
                        onClick = {
                            viewModel.updateService(service.id)
                            viewModel.sheetContentState = SheetContentState.Service(serviceFlow = viewModel.getServiceById(serviceId = service.id))
                            viewModel.isOpen = true
                        }
                    )
                }
            }
        }
    }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    if(viewModel.isOpen) {
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = {viewModel.isOpen = false},
            sheetState = sheetState,
            dragHandle = null,
            scrimColor = Color.Black.copy(0.6f),
            modifier = Modifier
                .displayCutoutPadding()
        ) {
            AnimatedContent(
                targetState = viewModel.sheetContentState,
                transitionSpec = {
                    fadeIn(tween(500)) togetherWith fadeOut(tween(500))
                }
            ) {
                when(it) {
                    is SheetContentState.NewService -> {
                        NewServiceUI()
                    }
                    is SheetContentState.Service -> {
                        UserServiceUI(it.serviceFlow, onDeleteServiceClick = {viewModel.isOpen = false})
                    }
                }
            }

        }
    }
}

//TODO: Сделать время кратным 15