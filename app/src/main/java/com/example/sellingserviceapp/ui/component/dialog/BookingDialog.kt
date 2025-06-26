package com.example.sellingserviceapp.ui.component.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import com.example.sellingserviceapp.R
import com.example.sellingserviceapp.model.domain.BookingWithData
import com.example.sellingserviceapp.ui.screen.order.DialogState
import com.example.sellingserviceapp.util.extension.imagePicker.ImageContent

@Composable
fun BookingDialogAsClient(
    booking: BookingWithData,
    dialogState: DialogState,
    description: String,
    onDismissRequest: () -> Unit,
    onRejectBookingButtonClick: (Int) -> Unit = {},
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        val part = booking.booking?.startDateTime?.split("T")
        var date = ""
        var time = ""
        if (part?.size != 0) {
            date = part?.get(0) ?: ""
            time = part?.get(1) ?: ""
            if (time.length > 5) {
                time = time.substring(0, 5)
            }
        }
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.onBackground.copy(0.1f)),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.5f)
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    IconButton(
                        modifier = Modifier.align(Alignment.TopEnd).padding(15.dp).zIndex(5f),
                        onClick = onDismissRequest
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Booking",
                            modifier = Modifier.size(28.dp),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    ImageContent(
                        photoBase64 = booking.service?.photo ?: "",
                        onEditButtonClick = {},
                        onMoreButtonClick = {},
                        onPickImageButtonClick = {},
                        isDropdownExpanded = false,
                        onDismissRequest = {},
                        onDeleteButtonClick = {},
                        isEditable = false,
                        isPickImage = false,
                        isMoreButton = false
                    )
                    Card(
                        modifier = Modifier.align(Alignment.BottomStart).zIndex(5f),
                        shape = RoundedCornerShape(topStart = 0.dp, topEnd = 20.dp, bottomStart = 0.dp, bottomEnd = 0.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background
                        )
                    ) {
                        Row (
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.calendar_month),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                date,fontSize = 17.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Icon(
                                painter = painterResource(R.drawable.aod_watch),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                time,fontSize = 17.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        /*Text(
                            text = booking.service?.tittle ?: "",
                            fontSize = 24.sp,
                            textAlign = TextAlign.Left,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.fillMaxWidth(0.75f).padding(start = 15.dp, end = 5.dp, top = 5.dp, bottom = 5.dp)
                        )*/
                    }
                }
                Text(
                    text = booking.service?.tittle ?: "",
                    fontSize = 28.sp,
                    textAlign = TextAlign.Left,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(horizontal = 15.dp)
                )
                Text(
                    modifier = Modifier.padding(horizontal = 15.dp),
                    text = description,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                    textAlign = TextAlign.Left
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    modifier = Modifier.padding(horizontal = 15.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        Column(
                            modifier = Modifier,
                            verticalArrangement = Arrangement.spacedBy(1.dp)
                        ) {
                            Text(
                                modifier = Modifier,
                                text = "${booking.user?.secondName} ${booking.user?.firstName}",
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
                                    text = booking.user?.email ?: "",
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
                                    text = "+7 ${booking.user?.phoneNumber}",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground.copy(0.8f)
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.padding(bottom = 10.dp).align(Alignment.Start),
                        horizontalArrangement = Arrangement.spacedBy(15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        when(dialogState) {
                            is DialogState.ConfirmedBooking -> {
                                OutlinedButton(
                                    onClick = { onRejectBookingButtonClick(booking.booking?.id?.toInt() ?: 0) },
                                    shape = RoundedCornerShape(14.dp),
                                    modifier = Modifier,
                                    contentPadding = PaddingValues(horizontal = 15.dp),
                                    border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground.copy(0.35f)),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = MaterialTheme.colorScheme.onBackground.copy(0.75f),
                                    )
                                ) {
                                    Text("Отменить запись", fontSize = 14.sp)
                                }
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookingDialogAsExecutor(
    booking: BookingWithData,
    dialogState: DialogState,
    description: String,
    onDismissRequest: () -> Unit,
    onRejectBookingButtonClick: (Int) -> Unit = {},
    onAcceptBookingButtonClick: (Int) -> Unit = {},
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        val part = booking.booking?.startDateTime?.split("T")
        var date = ""
        var time = ""
        if (part?.size != 0) {
            date = part?.get(0) ?: ""
            time = part?.get(1) ?: ""
            if (time.length > 5) {
                time = time.substring(0, 5)
            }
        }
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.onBackground.copy(0.1f)),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.5f)
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    IconButton(
                        modifier = Modifier.align(Alignment.TopEnd).padding(15.dp).zIndex(5f),
                        onClick = onDismissRequest
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Booking",
                            modifier = Modifier.size(28.dp),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    ImageContent(
                        photoBase64 = booking.service?.photo ?: "",
                        onEditButtonClick = {},
                        onMoreButtonClick = {},
                        onPickImageButtonClick = {},
                        isDropdownExpanded = false,
                        onDismissRequest = {},
                        onDeleteButtonClick = {},
                        isEditable = false,
                        isPickImage = false,
                        isMoreButton = false
                    )
                    Card(
                        modifier = Modifier.align(Alignment.BottomStart).zIndex(5f),
                        shape = RoundedCornerShape(topStart = 0.dp, topEnd = 20.dp, bottomStart = 0.dp, bottomEnd = 0.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background
                        )
                    ) {
                        Row (
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.calendar_month),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                date,fontSize = 17.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Icon(
                                painter = painterResource(R.drawable.aod_watch),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                time,fontSize = 17.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        /*Text(
                            text = booking.service?.tittle ?: "",
                            fontSize = 24.sp,
                            textAlign = TextAlign.Left,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.fillMaxWidth(0.75f).padding(start = 15.dp, end = 5.dp, top = 5.dp, bottom = 5.dp)
                        )*/
                    }
                }
                Text(
                    text = booking.service?.tittle ?: "",
                    fontSize = 28.sp,
                    textAlign = TextAlign.Left,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(horizontal = 15.dp)
                )
                Text(
                    modifier = Modifier.padding(horizontal = 15.dp),
                    text = description,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                    textAlign = TextAlign.Left
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    modifier = Modifier.padding(horizontal = 15.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        Column(
                            modifier = Modifier,
                            verticalArrangement = Arrangement.spacedBy(1.dp)
                        ) {
                            Text(
                                modifier = Modifier,
                                text = "${booking.user?.secondName} ${booking.user?.firstName}",
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
                                    text = booking.user?.email ?: "",
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
                                    text = "+7 ${booking.user?.phoneNumber}",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground.copy(0.8f)
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.padding(bottom = 10.dp).align(Alignment.Start),
                        horizontalArrangement = Arrangement.spacedBy(15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        when(dialogState) {
                            is DialogState.NewBooking -> {
                                OutlinedButton(
                                    onClick = { onRejectBookingButtonClick(booking.booking?.id?.toInt() ?: 0) },
                                    shape = RoundedCornerShape(14.dp),
                                    modifier = Modifier,
                                    contentPadding = PaddingValues(horizontal = 15.dp),
                                    border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground.copy(0.35f)),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = MaterialTheme.colorScheme.onBackground.copy(0.75f),
                                    )
                                ) {
                                    Text("Отклонить", fontSize = 14.sp)
                                }
                                Button(
                                    onClick = { onAcceptBookingButtonClick(booking.booking?.id?.toInt() ?: 0) },
                                    shape = RoundedCornerShape(14.dp),
                                    modifier = Modifier,
                                    contentPadding = PaddingValues(horizontal = 15.dp)
                                ) {
                                    Text("Подтвердить", fontSize = 14.sp)
                                }
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }
}