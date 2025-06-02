package com.example.sellingserviceapp.ui.screen.profile

import android.icu.util.Calendar
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.R
import com.example.sellingserviceapp.data.network.booking.Day
import com.example.sellingserviceapp.model.domain.UserDomain
import com.example.sellingserviceapp.ui.screen.profile.component.ActiveOrderItemButton
import com.example.sellingserviceapp.ui.screen.profile.component.HorizontalPagerItem
import com.example.sellingserviceapp.ui.screen.profile.component.Note
import com.example.sellingserviceapp.ui.screen.profile.component.Order
import com.example.sellingserviceapp.ui.screen.profile.component.SectionButton
import com.example.sellingserviceapp.ui.screen.profile.component.Setting
import com.example.sellingserviceapp.ui.screen.profile.component.SettingItemButton
import com.example.sellingserviceapp.ui.screen.profile.editProfile.EditProfileUI
import com.example.sellingserviceapp.util.extension.imagePicker.ImageContent
import com.example.sellingserviceapp.util.extension.imagePicker.pickImageLauncher
import com.vsnappy1.timepicker.data.model.TimePickerTime
import com.vsnappy1.timepicker.enums.MinuteGap
import com.vsnappy1.timepicker.ui.model.TimePickerConfiguration

sealed class ProfileSheetState {
    data object EditProfile: ProfileSheetState()
    data object TimeTable: ProfileSheetState()
}

sealed class PickTimeState {
    data object Start: PickTimeState()
    data object End: PickTimeState()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileUI(
    viewModel: ProfileViewModel = hiltViewModel(),
    onMyServiceButtonClick: () -> Unit
) {

    var isOpen by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    if(isOpen) {
        ModalBottomSheet(
            modifier = Modifier
                .displayCutoutPadding(),
            containerColor = MaterialTheme.colorScheme.background,
            sheetState = sheetState,
            onDismissRequest = {isOpen = false},
            scrimColor = Color.Black.copy(0.6f),
            dragHandle = null
        ) {
            AnimatedContent(
                targetState = viewModel.profileSheetState,
                transitionSpec = {
                    fadeIn(tween(500)) togetherWith fadeOut(tween(500))
                }
            ) {
                when(it) {
                    is ProfileSheetState.EditProfile -> {
                        EditProfileUI(
                            onBackButtonClick = {
                                isOpen = false
                            },
                            onSaveButtonClick = {
                                isOpen = false
                            }
                        )
                    }
                    is ProfileSheetState.TimeTable -> {
                        TimeTableUI()
                    }
                }
            }
        }
    }

    val user by viewModel.userFLow.collectAsState(initial = UserDomain.EMPTY)

    val pickImageLauncher = pickImageLauncher {
        viewModel.onPhotoSelected(it)
    }
    val bookingList by viewModel.bookingsAsExecutorFlow.collectAsState()

    Box(
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.background,
                RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .padding(bottom = 15.dp)
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.5f)
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Column(
                        modifier = Modifier
                            .zIndex(3f)
                            .align(Alignment.BottomStart)
                            .padding(15.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text("${user.firstName} ${user.secondName}", fontSize = 32.sp, color = MaterialTheme.colorScheme.onBackground)
                        Text(user.email, fontSize = 14.sp,color = MaterialTheme.colorScheme.onBackground.copy(0.7f))
                    }

                    ImageContent(
                        photoBase64 = user.avatar?: "",
                        onEditButtonClick = {
                            viewModel.profileSheetState = ProfileSheetState.EditProfile
                            isOpen = true
                        },
                        onMoreButtonClick = {},
                        onPickImageButtonClick = pickImageLauncher
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .padding(top = 15.dp),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    SectionButton(
                        modifier = Modifier.weight(1f),
                        onClick = onMyServiceButtonClick,
                        icon = painterResource(R.drawable.design_services),
                        title = "Мои услуги"
                    )
                    SectionButton(
                        modifier = Modifier.weight(1f),
                        onClick = {},
                        icon = painterResource(R.drawable.book),
                        title = "Мои записи"
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier.padding(horizontal = 15.dp),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    SectionButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            viewModel.profileSheetState = ProfileSheetState.TimeTable
                            isOpen = true
                        },
                        icon = painterResource(R.drawable.work_history),
                        title = "История"
                    )
                    SectionButton(
                        modifier = Modifier.weight(1f),
                        onClick = {},
                        icon = painterResource(R.drawable.notifications),
                        title = "Уведомления"
                    )
                }
            }

            item { //Min 8 Max 21
                val timeTable by viewModel.timeTable.collectAsState()
                var pickTimeState by remember { mutableStateOf<PickTimeState>(PickTimeState.Start) }
                var showTimePicker by remember { mutableStateOf(false) }
                val currentTime = Calendar.getInstance()
                val timePickerState = rememberTimePickerState(
                    initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
                    initialMinute = currentTime.get(Calendar.MINUTE),
                    is24Hour = true
                )
                var isSaveNewWorkTimeButtonEnabled by remember { mutableStateOf(false) }
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(15.dp),
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("График", fontSize = 24.sp, color = MaterialTheme.colorScheme.onBackground)
                            Button(
                                onClick = {
                                    viewModel.updateWorkTime()
                                    Log.d("NEW_WORK_TIME", "Start: ${viewModel.newWorkTime.startTime}\n " +
                                            "End: ${viewModel.newWorkTime.endTime}\n " +
                                            "IDS: ${viewModel.newWorkTime.daysIds}")
                                },
                                enabled = isSaveNewWorkTimeButtonEnabled,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                ),
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                                    modifier = Modifier.padding(horizontal = 15.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onBackground
                                    )
                                    Text(
                                        text = "Изменить",
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                        val days = listOf(
                            Day(id = 1, name = "ПН", code = ""),
                            Day(id = 2, name = "ВТ", code = ""),
                            Day(id = 3, name = "СР", code = ""),
                            Day(id = 4, name = "ЧТ", code = ""),
                            Day(id = 5, name = "ПТ", code = ""),
                            Day(id = 6, name = "СБ", code = ""),
                            Day(id = 7, name = "ВС", code = ""),
                        )
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            var daysIds = viewModel.newWorkTime.daysIds.toMutableList()

                            days.forEach { day ->
                                var selected by remember(timeTable) {
                                    mutableStateOf(
                                        timeTable?.days?.any { it.id == day.id } == true
                                    )
                                }
                                FilterChip(
                                    modifier = Modifier
                                        .height(34.dp)
                                        .width(60.dp),
                                    selected = selected,
                                    onClick = {
                                        selected = !selected
                                        isSaveNewWorkTimeButtonEnabled = true

                                        if(selected) {
                                            if(!daysIds.contains(day.id)) {
                                                daysIds.add(day.id)
                                            }
                                        } else {
                                            daysIds.remove(day.id)
                                        }

                                        viewModel.newWorkTime = viewModel.newWorkTime.copy(daysIds = daysIds)
                                        Log.d("NEW_WORK_TIME", "Start: ${viewModel.newWorkTime.startTime}\n " +
                                                "End: ${viewModel.newWorkTime.endTime}\n " +
                                                "IDS: ${viewModel.newWorkTime.daysIds}")
                                    },
                                    label = { Text(day.name?: "", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        containerColor = MaterialTheme.colorScheme.background.copy(0.5f),
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
                            Text("Рабочее время: ", color = MaterialTheme.colorScheme.onBackground)
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(
                                    onClick = {
                                        pickTimeState = PickTimeState.Start
                                        showTimePicker = true
                                    },
                                    contentPadding = PaddingValues(0.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.background.copy(0.8f)
                                    ),
                                    modifier = Modifier
                                        .height(32.dp)
                                        .width(70.dp),
                                    shape = RoundedCornerShape(12.dp),
                                ) {
                                    Text(timeTable?.startTime?: "")
                                }
                                Text(" : ")
                                Button(
                                    onClick = {
                                        pickTimeState = PickTimeState.End
                                        showTimePicker = true
                                    },
                                    contentPadding = PaddingValues(0.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.background.copy(0.8f)
                                    ),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .height(32.dp)
                                        .width(70.dp)
                                ) {
                                    Text(timeTable?.endTime?: "")
                                }
                            }
                        }

                        if (showTimePicker) {
                            when(pickTimeState) {
                                is PickTimeState.Start -> {
                                    PickTime(
                                        title = "Начало дня",
                                        time = timeTable?.startTime ?: "",
                                        onTimeSelected = { hour, minute ->
                                            viewModel.inputStartTime(hour, minute)
                                            isSaveNewWorkTimeButtonEnabled = true
                                        },
                                        onDismissRequest = { showTimePicker = false },
                                        onDismissButtonCLick = { showTimePicker = false },
                                        onConfirmButtonClick = {
                                            pickTimeState = PickTimeState.End
                                        }
                                    )
                                }
                                is PickTimeState.End -> {
                                    PickTime(
                                        title = "Конец дня",
                                        time = timeTable?.endTime ?: "",
                                        onTimeSelected = { hour, minute ->
                                            viewModel.inputEndTime(hour, minute)
                                            isSaveNewWorkTimeButtonEnabled = true
                                        },
                                        onDismissRequest = { showTimePicker = false },
                                        onDismissButtonCLick = { showTimePicker = false },
                                        onConfirmButtonClick = {
                                            showTimePicker = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    modifier = Modifier.padding(end = 15.dp, start = 15.dp),
                    text = "Ждут действия",
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            items(bookingList?: emptyList()) { booking ->
                ActiveOrderItemButton(
                    onClick = {},
                    booking = booking
                )
            }

            item {
                var settingsList by remember { mutableStateOf(
                    listOf(
                        Setting(
                            tittle = "Маркетинг",
                            description = "Отзывы•Напоминания•Сертификаты"
                        ),
                        Setting(
                            tittle = "Поддержка",
                            description = "Ответим на ваши вопросы"
                        ),
                        Setting(
                            tittle = "Профиль",
                            description = "Управление профилем"
                        ),
                    )
                )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                        .fillMaxWidth()
                ) {
                    Column {
                        Text(
                            modifier = Modifier.padding(top = 15.dp, start = 15.dp, end = 15.dp),
                            text = "Настройки",
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        settingsList.forEach { setting ->
                            SettingItemButton(
                                onClick = {},
                                setting = setting,
                                divider = setting != settingsList.last()
                            )
                        }
                    }
                }
            }

            item {
                SettingItemButton(
                    onClick = {viewModel.logout()},
                    setting = Setting(
                        tittle = "Выйти",
                        description = "Выход из профиля",
                        icon = R.drawable.logout
                    ),
                    divider = false
                )
            }
        }
    }

    //region Карусель уведомлений
    /*
    val notifications = listOf(
        Note("Системное уведомление", "У вас 3 новых чего-то там заюерите сейчас пока у вас не забрали и куча ошибок"),
        Note("Новый заказ", "Вам пришел новый заказа от пользователя"),
        Note("Имя записи", "Специалист подтвердил вашу запись")
    )
    val pagerState = rememberPagerState(initialPage = 0, pageCount = {notifications.size})
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        HorizontalPager(
            pageSpacing = 5.dp,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 15.dp),
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            HorizontalPagerItem(
                notifications = notifications,
                page = page
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(notifications.size) { index ->
                val size by animateDpAsState(
                    targetValue = if (pagerState.currentPage == index) 10.dp else 8.dp
                )
                val color by animateColorAsState(
                    targetValue = if (pagerState.currentPage == index)
                        MaterialTheme.colorScheme.onBackground
                    else
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )

                Box(
                    modifier = Modifier
                        .size(size)
                        .background(color, CircleShape)
                        .padding(2.dp)
                )
                if (index < notifications.size - 1) Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }*/
    //endregion
}

@Composable
fun PickTime(
    title: String,
    time: String,
    onTimeSelected: (Int, Int) -> Unit,
    onDismissRequest: () -> Unit,
    onDismissButtonCLick: () -> Unit,
    onConfirmButtonClick: () -> Unit
) {
    val parts = time.split(":")
    var hour = "5"
    var minute = "5"
    if (parts.size == 2) {
        hour = parts[0]
        minute = parts[1]
    }

    AlertDialog(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        shape = RoundedCornerShape(20.dp),
        tonalElevation = 50.dp,
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = onConfirmButtonClick,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Выбрать", fontSize = 20.sp)
            }
        },
        text = {
            Column {
                com.vsnappy1.timepicker.TimePicker(
                    onTimeSelected = onTimeSelected,
                    is24Hour = true,
                    minuteGap = MinuteGap.FIVE,
                    time = TimePickerTime(
                        hour = hour.toInt(),
                        minute = minute.toInt()
                    ),
                    configuration = TimePickerConfiguration.Builder()
                        .selectedTimeAreaColor(MaterialTheme.colorScheme.onBackground.copy(0.3f))
                        .selectedTimeAreaShape(RoundedCornerShape(20.dp))
                        .timeTextStyle(TextStyle(color = MaterialTheme.colorScheme.onBackground.copy(0.7f), fontSize = 18.sp))
                        .selectedTimeTextStyle(TextStyle(color = MaterialTheme.colorScheme.onBackground, fontSize = 20.sp))
                        .build()
                )
            }
        },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    fontSize = 28.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                )
                IconButton(
                    onClick = onDismissButtonCLick
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    )
}