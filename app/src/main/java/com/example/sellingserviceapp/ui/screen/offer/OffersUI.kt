package com.example.sellingserviceapp.ui.screen.offer

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.R
import com.example.sellingserviceapp.model.domain.BookingWithData
import com.example.sellingserviceapp.model.mapper.BookingStatusMapper
import com.example.sellingserviceapp.ui.component.dialog.BookingDialogAsClient
import com.example.sellingserviceapp.ui.screen.main.component.shimmerBrush
import com.example.sellingserviceapp.ui.screen.order.DialogState

sealed class BookingState {
    data object Init: BookingState()
    data object Loaded: BookingState()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OffersUI(
    viewModel: OffersViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.getBookingAsClient()
    }

    val offers by viewModel.bookingsAsClientFlow.collectAsState()
    val statuses by viewModel.statusListFlow.collectAsState()

    AnimatedContent(
        targetState = viewModel.offerState,
        transitionSpec = {
            fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
        }
    ) {
        if (viewModel.isBookingPicked) {
            when(viewModel.pickedBookingDialogState) {
                DialogState.NewBooking -> {
                    BookingDialogAsClient(
                        booking = viewModel.pickedBooking,
                        description = "Отправили заказ исполнителю, ожидайте подтверждения.",
                        dialogState = viewModel.pickedBookingDialogState,
                        onDismissRequest = {viewModel.isBookingPicked = false},

                    )
                }
                DialogState.ConfirmedBooking -> {
                    BookingDialogAsClient(
                        booking = viewModel.pickedBooking,
                        description = "Исполнитель подтвердил заказ. Ожидайте выполнения услуги.",
                        dialogState = viewModel.pickedBookingDialogState,
                        onDismissRequest = {viewModel.isBookingPicked = false},
                        onRejectBookingButtonClick = { bookingId ->

                        }
                    )
                }
                DialogState.RejectedByClientBooking -> {
                    BookingDialogAsClient(
                        booking = viewModel.pickedBooking,
                        description = "Вы отменили запись на услугу.",
                        dialogState = viewModel.pickedBookingDialogState,
                        onDismissRequest = {viewModel.isBookingPicked = false},
                        )
                }
                DialogState.RejectedByExecutorBooking -> {
                    BookingDialogAsClient(
                        booking = viewModel.pickedBooking,
                        description = "Исполнитель отклонил выполнение услуги.",
                        dialogState = viewModel.pickedBookingDialogState,
                        onDismissRequest = {viewModel.isBookingPicked = false},
                    )
                }
                DialogState.Expired -> {
                    BookingDialogAsClient(
                        booking = viewModel.pickedBooking,
                        description = "Исполнитель не подтвердил выполнение заказа.",
                        dialogState = viewModel.pickedBookingDialogState,
                        onDismissRequest = {viewModel.isBookingPicked = false},
                    )
                }
                DialogState.Pending -> {
                    BookingDialogAsClient(
                        booking = viewModel.pickedBooking,
                        description = "Исполнитель приступил к выполнению заказа. Ожидайте завершения работы.",
                        dialogState = viewModel.pickedBookingDialogState,
                        onDismissRequest = {viewModel.isBookingPicked = false},
                    )
                }
                DialogState.Completed -> {
                    BookingDialogAsClient(
                        booking = viewModel.pickedBooking,
                        description = "Исполнитель выполнил ваш заказ. Чтобы оставить отзыв вернитесь на экран профиля и откройте экран 'Отзывы'",
                        dialogState = viewModel.pickedBookingDialogState,
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
                                    Text("Записи", fontSize = 32.sp, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold)
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
                                            viewModel.getBookingAsClient()
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
                        if (offers == emptyList<BookingWithData>()) {
                            item {
                                Text(
                                    "Нет записей.",
                                    modifier = Modifier.fillMaxWidth(),
                                    fontSize = 24.sp,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        } else {
                            items(offers) { offer ->
                                BookingListItem(
                                    offer = offer,
                                    isLoading = viewModel.isLoading,
                                    onClick = {
                                        viewModel.pickedBookingDialogState = BookingStatusMapper.bookingDialogStateMap(offer.booking?.status ?: "")
                                        viewModel.pickedBooking = offer
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

@Composable
fun BookingListItem(
    offer: BookingWithData,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    if (isLoading) {
        Box(
            modifier = Modifier
                .background(shimmerBrush(targetValue = 500f, showShimmer = true), shape = RoundedCornerShape(20.dp))
                .fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ServiceIcon(
                        photoBase64 = ""
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .height(25.dp)
                                .fillMaxWidth(0.8f)
                                .background(color = MaterialTheme.colorScheme.onBackground.copy(0.1f), shape = RoundedCornerShape(12.dp))
                        )
                        Box(
                            modifier = Modifier
                                .height(15.dp)
                                .fillMaxWidth(0.6f)
                                .background(color = MaterialTheme.colorScheme.onBackground.copy(0.1f), shape = RoundedCornerShape(12.dp))
                        )
                    }
                }
            }
        }
    } else {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(20.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ServiceIcon(
                        photoBase64 = offer.service?.photo ?: ""
                    )
                    Column {
                        Text(
                            modifier = Modifier,
                            text = offer.service?.tittle ?: "",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            modifier = Modifier,
                            text = offer.booking?.statusReason ?: "",
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
                Icon(
                    painter = painterResource(BookingStatusMapper.bookingStatusToIconMap(offer.booking?.status ?: "")),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

@Composable
fun ServiceIcon(
    photoBase64: String
) {
    Box(
        modifier = Modifier.size(60.dp).clip(RoundedCornerShape(14.dp))
    ) {
        if (photoBase64.isNotBlank()) {
            val bitmap = remember(photoBase64) {
                try {
                    val bytes = Base64.decode(photoBase64, Base64.DEFAULT)
                    BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                } catch (e: Exception) {
                    null
                }
            }

            bitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Аватар пользователя",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        } else {
            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize(0.7f),
                painter = painterResource(R.drawable.book),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

    }
}