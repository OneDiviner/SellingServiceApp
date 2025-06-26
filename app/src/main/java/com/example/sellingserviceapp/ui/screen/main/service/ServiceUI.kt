package com.example.sellingserviceapp.ui.screen.main.service

import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.model.FeedbackWithData
import com.example.sellingserviceapp.ui.component.button.LargeButton
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonModel
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonState
import com.example.sellingserviceapp.ui.screen.createService.component.ServiceInfoRow
import com.example.sellingserviceapp.ui.screen.main.component.shimmerBrush
import com.example.sellingserviceapp.ui.screen.main.service.booking.BookingUI
import com.example.sellingserviceapp.ui.screen.main.service.booking.confirmBooking.ConfirmBookingUI
import com.example.sellingserviceapp.ui.screen.profile.component.ProfileIconButton
import com.example.sellingserviceapp.util.extension.imagePicker.ImageContent

sealed class ServiceUIState {
    data object Init: ServiceUIState()
    data object Loaded: ServiceUIState()
}

sealed class ServiceSheetContentState {
    data object ConfirmBooking: ServiceSheetContentState()
    data object Booking: ServiceSheetContentState()
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceUI(
    serviceId: Int,
    viewModel: ServiceViewModel = hiltViewModel()
) {

    LaunchedEffect(serviceId) {
        viewModel.init(serviceId)
    }

    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    var isOpen by remember { mutableStateOf(false) }
    if (isOpen) {
        ModalBottomSheet(
            sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true
            ),
            onDismissRequest = {isOpen = false},
            containerColor = MaterialTheme.colorScheme.background,
            scrimColor = Color.Black.copy(0.6f),
            dragHandle = null,
            modifier = Modifier
                .displayCutoutPadding(),
        ) {
            AnimatedContent(
                targetState = viewModel.serviceSheetContentState,
                transitionSpec = {
                    fadeIn(tween(500)) togetherWith fadeOut(tween(500))
                }
            ) {
                when(it) {
                    is ServiceSheetContentState.Booking -> {
                        BookingUI(
                            viewModel.service.id,
                            userId = viewModel.service.userId,
                            onBookButtonClick = {bookingDate, bookingTime ->
                                date = bookingDate
                                time = bookingTime
                                viewModel.serviceSheetContentState = ServiceSheetContentState.ConfirmBooking
                            },
                            onBackButtonClick = {isOpen = false}
                        )
                    }
                    is ServiceSheetContentState.ConfirmBooking -> {
                        ConfirmBookingUI(
                            serviceExternal = viewModel.service,
                            userExternal = viewModel.user,
                            dateExternal = date,
                            timeExternal = time,
                            onBackButtonClick = {viewModel.serviceSheetContentState = ServiceSheetContentState.Booking},
                            onCreateBookingClick = {
                                isOpen = false
                                viewModel.serviceSheetContentState = ServiceSheetContentState.Booking
                            }
                        )
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .padding(bottom = 15.dp)
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
    ) {
        AnimatedContent(
            targetState = viewModel.serviceUIState,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
            }
        ) {
            when(it) {
                ServiceUIState.Init -> {
                    ServiceUISkeleton()
                }
                ServiceUIState.Loaded -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
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
                                ImageContent(
                                    photoBase64 = viewModel.service.photo ?: "",
                                    onEditButtonClick = {
                                        Log.d("SERVICE_ID", viewModel.service.id.toString())
                                        //viewModel.serviceState = ServiceState.EditService
                                    },
                                    onMoreButtonClick = {
                                        isDropDown = !isDropDown
                                    },
                                    onPickImageButtonClick = {},
                                    isDropdownExpanded = isDropDown,
                                    onDismissRequest = { isDropDown = false },
                                    onDeleteButtonClick = {

                                    },
                                    isEditable = false,
                                    isPickImage = false
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
                                    text = "${viewModel.service.price}₽ за ${viewModel.service.priceTypeName}",
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    modifier = Modifier,
                                    text =  viewModel.service.tittle,
                                    fontSize = 24.sp,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Row(
                                    modifier = Modifier.padding(bottom = 10.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = Color.Yellow,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Text(
                                        modifier = Modifier,
                                        text = if (viewModel.rating == 0.0) "Нет отзывов" else viewModel.rating.toString(),
                                        fontSize = 24.sp,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                                ServiceInfoRow(
                                    title = "Категория",
                                    value = viewModel.service.categoryName
                                )
                                ServiceInfoRow(
                                    title = "Подкатегория",
                                    value = viewModel.service.subcategoryName
                                )
                                ServiceInfoRow(
                                    title = "Длительность",
                                    value = "${viewModel.service.duration.toString()} минут"
                                )
                                ServiceInfoRow(
                                    title = "Формат оказания услуги",
                                    values = viewModel.service.formats?: emptyList()
                                )
                            }
                        }
                        item {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(5.dp),
                                modifier = Modifier.padding(horizontal = 15.dp)
                            ) {
                                Text(
                                    text = "Описание",
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = viewModel.service.description?: "Error",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.ExtraLight,
                                    color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                                    // modifier = Modifier.padding(15.dp)
                                )

                            }
                        }
                        item {
                            Column(
                                modifier = Modifier.padding(horizontal = 15.dp),
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                                ) {
                                    ProfileIconButton(
                                        onClick = {},
                                        photoBase64 = viewModel.user.avatar ?: ""
                                    )
                                    Column(
                                        modifier = Modifier,
                                        verticalArrangement = Arrangement.spacedBy(1.dp)
                                    ) {
                                        Text(
                                            modifier = Modifier,
                                            text = "${viewModel.user.secondName} ${viewModel.user.firstName}",
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                                        ) {
                                            Icon(
                                                modifier = Modifier.size(18.dp),
                                                imageVector = Icons.Default.Email,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.onBackground.copy(0.8f)
                                            )
                                            Text(
                                                modifier = Modifier,
                                                text = viewModel.user.email,
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onBackground.copy(0.8f)
                                            )
                                        }
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                                        ) {
                                            Icon(
                                                modifier = Modifier.size(18.dp),
                                                imageVector = Icons.Default.Call,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.onBackground.copy(0.8f)
                                            )
                                            Text(
                                                modifier = Modifier,
                                                text = "+7 ${viewModel.user.phoneNumber}",
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onBackground.copy(0.8f)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        item {
                            LargeButton(
                                modifier = Modifier
                                    .padding(horizontal = 15.dp),
                                model = ButtonModel("Записаться", state = ButtonState.Ready),
                                onClick = {
                                    viewModel.serviceSheetContentState = ServiceSheetContentState.Booking
                                    isOpen = true
                                }
                            )
                        }
                        item {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(15.dp),
                                modifier = Modifier.padding(horizontal = 15.dp)
                            ) {
                                Text("Отзывы", fontSize = 28.sp, color = MaterialTheme.colorScheme.onBackground)
                                if (viewModel.feedbackList == emptyList<FeedbackWithData>()) {
                                    Text(
                                        "Нет отзывов.",
                                        modifier = Modifier.fillMaxWidth(),
                                        fontSize = 24.sp,
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                } else {
                                    viewModel.feedbackList.forEach { feedback ->
                                        Column(
                                            verticalArrangement = Arrangement.spacedBy(10.dp)
                                        ) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(15.dp)
                                            ) {
                                                ProfileIconButton(
                                                    onClick = {},
                                                    photoBase64 = feedback.user.avatar ?: ""
                                                )
                                                Column(
                                                    modifier = Modifier,
                                                    verticalArrangement = Arrangement.spacedBy(1.dp)
                                                ) {
                                                    Row(
                                                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                                                    ) {
                                                        Text(
                                                            modifier = Modifier,
                                                            text = "${feedback.user.secondName} ${feedback.user.firstName}",
                                                            fontSize = 20.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = MaterialTheme.colorScheme.onBackground
                                                        )
                                                        Row(
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                                                        ) {
                                                            Icon(
                                                                modifier = Modifier.size(18.dp),
                                                                imageVector = Icons.Default.Star,
                                                                contentDescription = null,
                                                                tint = MaterialTheme.colorScheme.onBackground.copy(0.8f)
                                                            )
                                                            Text(
                                                                modifier = Modifier,
                                                                text = feedback.feedback.rating.toString(),
                                                                fontSize = 15.sp,
                                                                fontWeight = FontWeight.Bold,
                                                                color = MaterialTheme.colorScheme.onBackground.copy(0.8f)
                                                            )
                                                        }
                                                    }
                                                    Text(
                                                        modifier = Modifier,
                                                        text = feedback.feedback.createdAt,
                                                        fontSize = 15.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = MaterialTheme.colorScheme.onBackground.copy(0.8f)
                                                    )
                                                }
                                            }
                                            Card(
                                                colors = CardDefaults.cardColors(
                                                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                                                ),
                                                border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground.copy(0.1f))
                                            ) {
                                                Text(feedback.feedback.comment, modifier = Modifier.padding(10.dp), fontSize = 14.sp)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}