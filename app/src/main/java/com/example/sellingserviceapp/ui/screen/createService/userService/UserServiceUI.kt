package com.example.sellingserviceapp.ui.screen.createService.userService

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.R
import com.example.sellingserviceapp.model.domain.ServiceDomain
import com.example.sellingserviceapp.ui.component.circularProgressIndicator.FullScreenCircularProgressIndicator
import com.example.sellingserviceapp.ui.screen.createService.component.ServiceInfoRow
import com.example.sellingserviceapp.ui.screen.createService.userService.editService.EditServiceUI
import com.example.sellingserviceapp.ui.screen.main.service.ServiceUISkeleton
import com.example.sellingserviceapp.util.extension.imagePicker.ImageContent
import com.example.sellingserviceapp.util.extension.imagePicker.pickImageLauncher
import kotlinx.coroutines.flow.Flow

sealed class ServiceState {
    data object Service: ServiceState()
    data object EditService: ServiceState()
    data object Loading: ServiceState()
    data object Error: ServiceState()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserServiceUI(
    serviceFlow: Flow<ServiceDomain>,
    onDeleteServiceClick: () -> Unit,
    viewModel: UserServiceViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = serviceFlow) {
        serviceFlow.collect { newServiceDomain -> // Собираем каждое новое значение
            viewModel.updateServiceDomain(newServiceDomain) // Вызываем функцию во ViewModel
        }
    }

    val service by viewModel.serviceFlow.collectAsState()
    val pickImageLauncher = pickImageLauncher {
        viewModel.onPhotoSelected(service.id, it)
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            viewModel.serviceState = ServiceState.Service
        }
    }

    AnimatedContent(
        targetState = viewModel.serviceState,
        transitionSpec = {
            fadeIn(tween(500)) togetherWith fadeOut(tween(500))
        }
    ) {
        when(it) {
            is ServiceState.Service -> {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .padding(bottom = 15.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .displayCutoutPadding(),
                        verticalArrangement = Arrangement.spacedBy(30.dp)
                    ) {
                        item {
                            var isDropDown by remember { mutableStateOf<Boolean>(false) }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1.5f)
                                    .background(MaterialTheme.colorScheme.surfaceContainer),
                                contentAlignment = Alignment.BottomEnd
                            ) {
                                BottomSheetDefaults.DragHandle(
                                    modifier = Modifier
                                        .displayCutoutPadding()
                                        .zIndex(3f)
                                        .align(Alignment.TopCenter)
                                )
                                Card(
                                    modifier = Modifier
                                        .padding(start = 15.dp)
                                        .offset(y = 18.dp)
                                        .zIndex(3f)
                                        .align(Alignment.BottomStart),
                                    shape = RoundedCornerShape(14.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primary
                                    )
                                ) {
                                    Text(
                                        text = service.statusName,
                                        fontSize = 16.sp,
                                        modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
                                    )
                                }
                                ImageContent(
                                    photoBase64 = service.photo ?: "",
                                    onEditButtonClick = {
                                        Log.d("SERVICE_ID", service.id.toString())
                                        viewModel.serviceState = ServiceState.EditService
                                                        },
                                    onMoreButtonClick = {
                                        isDropDown = !isDropDown
                                    },
                                    onPickImageButtonClick = pickImageLauncher,
                                    isDropdownExpanded = isDropDown,
                                    onDismissRequest = { isDropDown = false },
                                    onDeleteButtonClick = {
                                        onDeleteServiceClick()
                                        viewModel.deleteService()
                                    },
                                    isGenerateImage = true,
                                    isGenerating = viewModel.isGenerating,
                                    onGenerateImageButtonClick = {
                                        viewModel.generateImage()
                                    }
                                )
                            }
                        }
                        item {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(5.dp),
                                modifier = Modifier.padding(horizontal = 15.dp)
                            ) {
                                Text(
                                    modifier = Modifier,
                                    text = "${service.price}₽ за ${service.priceTypeName}",
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    modifier = Modifier.padding(bottom = 10.dp),
                                    text =  service.tittle,
                                    fontSize = 24.sp,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                ServiceInfoRow(
                                    title = "Категория",
                                    value = service.categoryName
                                )
                                ServiceInfoRow(
                                    title = "Подкатегория",
                                    value = service.subcategoryName
                                )
                                ServiceInfoRow(
                                    title = "Длительность",
                                    value = "${service.duration.toString()} минут"
                                )
                                ServiceInfoRow(
                                    title = "Формат оказания услуги",
                                    values = service.formats?: emptyList()
                                )
                            }
                        }
                        item {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(5.dp),
                                modifier = Modifier.padding(horizontal = 15.dp)
                            ) {
                                Text("Описание")
                                Text(
                                    text = service.description?: "Error",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.ExtraLight,
                                    color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                                    // modifier = Modifier.padding(15.dp)
                                )

                            }
                        }
                    }
                }
            }
            is ServiceState.EditService -> {
                EditServiceUI(
                    service = service,
                    onBackButtonClick = {viewModel.serviceState = ServiceState.Service},
                    onEditButtonClick = {viewModel.serviceState = ServiceState.Service},
                    categories = viewModel.categories,
                    formats = viewModel.formats,
                    priceTypes = viewModel.priceTypes
                )

            }
            is ServiceState.Loading -> {
                ServiceUISkeleton()
            }
            is ServiceState.Error -> {

            }
        }
    }
}