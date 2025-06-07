package com.example.sellingserviceapp.ui.component.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sellingserviceapp.R
import com.example.sellingserviceapp.model.domain.BookingWithData
import com.example.sellingserviceapp.model.mapper.BookingStatusMapper

@Composable
fun RejectedBookingDialog(
    booking: BookingWithData,
    description: String,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.background,
        shape = RoundedCornerShape(20.dp),
        tonalElevation = 50.dp,
        onDismissRequest = onDismissRequest,
        confirmButton = {},
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Text(
                    text = description,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = "${booking.user?.secondName} ${booking.user?.firstName}",
                        fontSize = 20.sp
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "PhoneNumber",
                            modifier = Modifier.size(18.dp),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "+7${booking.user?.phoneNumber}",
                            fontSize = 16.sp
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email",
                            modifier = Modifier.size(18.dp),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "${booking.user?.email}",
                            fontSize = 16.sp
                        )
                    }
                }
            }
        },
        title = {
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
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    IconButton(
                        modifier = Modifier.align(Alignment.TopEnd),
                        onClick = onDismissRequest
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Booking",
                            modifier = Modifier.size(28.dp),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Icon(
                        painter = painterResource(R.drawable.book),
                        contentDescription = "Booking",
                        modifier = Modifier.size(48.dp).align(Alignment.Center),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }

                Text(
                    text = booking.service?.tittle ?: "",
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = BookingStatusMapper.statusReasonByExecutorMap(booking.booking?.statusReason ?: ""),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        Text("Дата: ", fontSize = 14.sp)
                        Text(
                            date,fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Row {
                        Text("Время: ",fontSize = 14.sp)
                        Text(
                            time,fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    )
}