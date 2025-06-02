package com.example.sellingserviceapp.ui.screen.main.service.booking.confirmBooking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.example.sellingserviceapp.model.domain.ServiceDomain
import com.example.sellingserviceapp.model.domain.UserDomain
import com.example.sellingserviceapp.ui.screen.createService.component.SheetStickyHeader
import com.example.sellingserviceapp.ui.screen.profile.component.ProfileIconButton

@Composable
fun ConfirmBookingUI(
    serviceExternal: ServiceDomain,
    userExternal: UserDomain,
    dateExternal: String,
    timeExternal: String,
    onCreateBookingClick: () -> Unit,
    onBackButtonClick: () -> Unit,
    viewModel: ConfirmBookingViewModel = hiltViewModel()
) {
    LaunchedEffect(serviceExternal, userExternal, dateExternal, timeExternal) {
        viewModel.initData(serviceExternal, userExternal, dateExternal, timeExternal)
    }

    val service by viewModel.serviceFlow.collectAsState()
    val user by viewModel.userFlow.collectAsState()
    val date by viewModel.dateFlow.collectAsState()
    val time by viewModel.timeFlow.collectAsState()

    Box(
        modifier = Modifier.padding(all = 15.dp),
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            stickyHeader {
                SheetStickyHeader(
                    title = "Подтверждение",
                    onBackButtonClick = onBackButtonClick
                )
            }
            item {
                Column {
                    Text(service.tittle, fontSize = 24.sp, color = MaterialTheme.colorScheme.onBackground)
                    Text("${service.price}₽ за ${service.priceTypeName}", fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground.copy(0.8f))
                }
            }
            item {
                Column {
                    Row {
                        Text("Дата записи: ", fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground.copy(0.7f))
                        Text(date, fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground)
                    }
                    Row {
                        Text("Время записи: ", fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground.copy(0.7f))
                        Text(time, fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground)
                    }
                }

            }
            item {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        ProfileIconButton(
                            onClick = {},
                            photoBase64 = user.avatar ?: ""
                        )
                        Column(
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                modifier = Modifier,
                                text = "${user.secondName} ${user.firstName}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = Icons.Default.Call,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    modifier = Modifier,
                                    text = "+7 ${user.phoneNumber}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground.copy(0.8f)
                                )
                            }
                        }
                    }
                }
            }
            item {
                Button(
                    onClick = {
                        viewModel.createBooking()
                        onCreateBookingClick()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors()
                ) {
                    Text("Записаться", fontSize = 20.sp)
                }
            }
        }
    }
}