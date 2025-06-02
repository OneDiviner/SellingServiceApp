package com.example.sellingserviceapp.ui.screen.profile

import android.icu.util.Calendar
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.data.network.booking.Day

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeTableUI (
    viewModel: ProfileViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.getTimeTable()
    }

    val timeTable by viewModel.timeTable.collectAsState()
    var showTimePicker by remember { mutableStateOf(false) }
    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true
    )

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            Box(modifier = Modifier.fillMaxWidth()) {
                BottomSheetDefaults.DragHandle(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                )
            }

            Text("График", fontSize = 28.sp)

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val items = listOf(
                    Day(id = 1, "ПН"),
                    Day(id = 2, "ВТ"),
                    Day(id = 3, "СР"),
                    Day(id = 4, "ЧТ"),
                    Day(id = 5, "ПТ"),
                    Day(id = 6, "СБ"),
                    Day(id = 7, "ВС"),
                    )
                items.forEach { item ->
                    var selected by remember(timeTable) {
                        mutableStateOf(
                            timeTable?.days?.any { dayInTable -> dayInTable.id == item.id } ?: false
                        )
                    }
                    FilterChip(
                        modifier = Modifier
                            .height(40.dp)
                            .width(60.dp),
                        selected = selected,
                        onClick = {selected = !selected},
                        label = { Text(item.code, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                        ),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground.copy(0.15f))
                    )
                }

            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Время")
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {},
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer
                        ),
                        modifier = Modifier
                            .height(32.dp)
                            .width(70.dp),
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Text("9:00")
                    }
                    Text(" : ")
                    Button(
                        onClick = {showTimePicker = true},
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .height(32.dp)
                            .width(70.dp)
                    ) {
                        Text("18:00")
                    }
                }
            }




            if (showTimePicker) {
                AlertDialog(
                    onDismissRequest = {showTimePicker = false},
                    dismissButton = {
                        IconButton(
                            onClick = {showTimePicker = false}
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                showTimePicker = false
                            }
                        ) {
                            Text("ok")
                        }
                    },
                    text = {
                        TimePicker(
                            state = timePickerState
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    title = {Text("title")}

                )
            }

            Button(
                onClick = {},
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
            ) {
                Text("Изменить график", fontSize = 20.sp)
            }
        }
    }


}