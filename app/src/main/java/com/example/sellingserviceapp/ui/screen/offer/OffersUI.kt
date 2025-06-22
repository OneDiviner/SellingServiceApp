package com.example.sellingserviceapp.ui.screen.offer

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
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.model.mapper.BookingStatusAsClientMapper
import com.example.sellingserviceapp.model.mapper.BookingStatusMapper
import com.example.sellingserviceapp.ui.component.dialog.ConfirmedBookingDialog
import com.example.sellingserviceapp.ui.component.dialog.NewBookingDialog
import com.example.sellingserviceapp.ui.component.dialog.RejectedBookingDialog
import com.example.sellingserviceapp.ui.screen.createService.component.CategoryButton
import com.example.sellingserviceapp.ui.screen.order.DialogState
import com.example.sellingserviceapp.ui.screen.order.OrdersViewModel

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

    if (viewModel.isBookingPicked) {
        when(viewModel.pickedBookingDialogState) {
            DialogState.NewBooking -> {
                NewBookingDialog(
                    booking = viewModel.pickedBooking,
                    onDismissRequest = {viewModel.isBookingPicked = false},
                    onConfirmButtonClick = { id ->
                        viewModel.isBookingPicked = false

                    },
                    onDismissButtonClick = { id ->
                        viewModel.isBookingPicked = false

                    }
                )
            }
            DialogState.ConfirmedBooking -> {
                ConfirmedBookingDialog(
                    booking = viewModel.pickedBooking,
                    isReject = false,
                    onDismissRequest = {viewModel.isBookingPicked = false},
                    onDismissButtonClick = { id ->
                        viewModel.isBookingPicked = false
                    }
                )
            }
            DialogState.RejectedByClientBooking -> {
                RejectedBookingDialog(
                    booking = viewModel.pickedBooking,
                    description = "Вы отменили запись на услугу.",
                    onDismissRequest = {viewModel.isBookingPicked = false}
                )
            }
            DialogState.RejectedByExecutorBooking -> {
                RejectedBookingDialog(
                    booking = viewModel.pickedBooking,
                    description = "Исполнитель отклонил выполнение услуги.",
                    onDismissRequest = {viewModel.isBookingPicked = false}
                )
            }
        }
    }

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
                    Button(
                        onClick = {

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
                            Text("История", fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground)
                        }

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
                                viewModel.getBookingAsClient(item.id)
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
            items(offers) { offer ->
                CategoryButton(
                    category = offer.service?.tittle ?: "",
                    description = offer.booking?.statusReason ?: "",
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