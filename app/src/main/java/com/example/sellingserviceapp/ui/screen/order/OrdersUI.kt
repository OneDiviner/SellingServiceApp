package com.example.sellingserviceapp.ui.screen.order

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.model.domain.BookingWithData
import com.example.sellingserviceapp.model.mapper.BookingStatusMapper
import com.example.sellingserviceapp.ui.component.dialog.BookingDialogAsExecutor
import com.example.sellingserviceapp.ui.screen.offer.BookingListItem
import com.example.sellingserviceapp.ui.screen.offer.BookingSkeleton
import com.example.sellingserviceapp.ui.screen.offer.BookingState

sealed class DialogState {
    data object NewBooking: DialogState()
    data object ConfirmedBooking: DialogState()
    data object RejectedByExecutorBooking: DialogState()
    data object RejectedByClientBooking: DialogState()
    data object Pending: DialogState()
    data object Completed: DialogState()
    data object Expired: DialogState()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersUI(
    viewModel: OrdersViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.getBookingAsExecutor()
    }

    val orders by viewModel.bookingsAsExecutorFlow.collectAsState()
    val statuses by viewModel.statusListFlow.collectAsState()



    AnimatedContent(
        targetState = viewModel.orderState,
        transitionSpec = {
            fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
        }
    ) {
        if (viewModel.isBookingPicked) {
            when(viewModel.pickedBookingDialogState) {
                DialogState.NewBooking -> {
                    BookingDialogAsExecutor(
                        booking = viewModel.pickedBooking,
                        dialogState = viewModel.pickedBookingDialogState,
                        description = "У вас новый заказа. Подвердите его выполнение.",
                        onDismissRequest = {viewModel.isBookingPicked = false},
                        onAcceptBookingButtonClick = { id ->
                            viewModel.isBookingPicked = false
                            viewModel.confirmBooking(id)
                        },
                        onRejectBookingButtonClick = { id ->
                            viewModel.isBookingPicked = false
                            viewModel.rejectBooking(id)
                        }
                    )
                }
                DialogState.ConfirmedBooking -> {
                    BookingDialogAsExecutor(
                        booking = viewModel.pickedBooking,
                        dialogState = viewModel.pickedBookingDialogState,
                        description = "Вы подтвердили выполнение заказа.",
                        onDismissRequest = {viewModel.isBookingPicked = false},
                    )
                }
                DialogState.RejectedByClientBooking -> {
                    BookingDialogAsExecutor(
                        booking = viewModel.pickedBooking,
                        dialogState = viewModel.pickedBookingDialogState,
                        description = "Клиент отменил запись на услугу.",
                        onDismissRequest = {viewModel.isBookingPicked = false},
                    )
                }
                DialogState.RejectedByExecutorBooking -> {
                    BookingDialogAsExecutor(
                        booking = viewModel.pickedBooking,
                        dialogState = viewModel.pickedBookingDialogState,
                        description = "Вы отклонили выполнение услуги.",
                        onDismissRequest = {viewModel.isBookingPicked = false},
                    )
                }
                DialogState.Expired -> {
                    BookingDialogAsExecutor(
                        booking = viewModel.pickedBooking,
                        dialogState = viewModel.pickedBookingDialogState,
                        description = "Вы не подтвердили выполнение заказа.",
                        onDismissRequest = {viewModel.isBookingPicked = false},
                    )
                }
                DialogState.Pending -> {
                    BookingDialogAsExecutor(
                        booking = viewModel.pickedBooking,
                        dialogState = viewModel.pickedBookingDialogState,
                        description = "Вы приступили к выполнению заказа.",
                        onDismissRequest = {viewModel.isBookingPicked = false},
                    )
                }
                DialogState.Completed -> {
                    BookingDialogAsExecutor(
                        booking = viewModel.pickedBooking,
                        dialogState = viewModel.pickedBookingDialogState,
                        description = "Выполнение заказа завершено.",
                        onDismissRequest = {viewModel.isBookingPicked = false},
                    )
                }
            }
        }
        when(it) {
            BookingState.Init -> {
                BookingSkeleton()
            }
            BookingState.Loaded -> {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                        .fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .displayCutoutPadding()
                            .padding(horizontal = 15.dp),
                        verticalArrangement = Arrangement.spacedBy(15.dp),
                    ) {
                        stickyHeader {
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
                                    Text("Заказы", fontSize = 32.sp, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        item {
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                items(statuses) { item ->

                                    val isSelected = viewModel.isFilterSelected == item.id

                                    FilterChip(
                                        modifier = Modifier.fillMaxHeight(),
                                        selected = isSelected,
                                        onClick = {
                                            viewModel.isFilterSelected = if (isSelected) null else item.id
                                            viewModel.getBookingAsExecutor()
                                        },
                                        label = {Text(item.name?: "")},
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                                        ),
                                        shape = RoundedCornerShape(18.dp),
                                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground.copy(0.15f))
                                    )
                                }
                            }
                        }
                        if (orders == emptyList<BookingWithData>()) {
                            item {
                                Text(
                                    "Нет заказов.",
                                    modifier = Modifier.fillMaxWidth(),
                                    fontSize = 24.sp,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        } else {
                            items(orders) { order ->
                                BookingListItem(
                                    offer = order,
                                    isLoading = viewModel.isLoading,
                                    onClick = {
                                        viewModel.pickedBookingDialogState = BookingStatusMapper.bookingDialogStateMap(order.booking?.status ?: "")
                                        viewModel.pickedBooking = order
                                        viewModel.isBookingPicked = true
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}