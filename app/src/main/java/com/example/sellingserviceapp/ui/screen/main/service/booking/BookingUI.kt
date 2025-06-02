package com.example.sellingserviceapp.ui.screen.main.service.booking

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.ui.screen.createService.component.ServiceTextField
import com.example.sellingserviceapp.ui.screen.createService.component.SheetStickyHeader
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.Year
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Date
import java.util.Locale

//region Utils
fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(Date(millis))
}

enum class TimeOfDayCategory(val displayName: String) {
    MORNING("Утро"),
    DAY("День"),
    EVENING("Вечер"),
    NIGHT("Ночь"),
    UNKNOWN("Неизвестно") // На случай, если время не попадет ни в одну категорию или будет некорректным
}

@RequiresApi(Build.VERSION_CODES.O)
fun getTimeOfDayCategory(timeString: String): TimeOfDayCategory {
    return try {
        val time = LocalTime.parse(timeString, DateTimeFormatter.ISO_LOCAL_TIME) // Парсим строку "HH:mm:ss"
        when {
            (time.isSameOrAfter(LocalTime.of(22, 0, 0)) || time.isBefore(
                LocalTime.of(6, 0, 0))) -> TimeOfDayCategory.NIGHT
            time.isSameOrAfter(LocalTime.of(6, 0, 0)) && time.isBefore(
                LocalTime.of(12, 0, 0)) -> TimeOfDayCategory.MORNING
            time.isSameOrAfter(LocalTime.of(12, 0, 0)) && time.isBefore(
                LocalTime.of(18, 0, 0)) -> TimeOfDayCategory.DAY
            time.isSameOrAfter(LocalTime.of(18, 0, 0)) && time.isBefore(
                LocalTime.of(22, 0, 0)) -> TimeOfDayCategory.EVENING
            else -> TimeOfDayCategory.UNKNOWN // Если время не попало в другие категории (маловероятно с LocalTime)
        }
    } catch (e: DateTimeParseException) {
        // Обработка случая, если строка времени некорректна
        println("Ошибка парсинга времени: $timeString - ${e.message}")
        TimeOfDayCategory.UNKNOWN
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalTime.isSameOrAfter(other: LocalTime): Boolean = !this.isBefore(other)

@RequiresApi(Build.VERSION_CODES.O)
fun mapApiDayNameToDayOfWeek(apiDayName: String?): DayOfWeek? {
    return when (apiDayName?.uppercase()) { // Приводим к верхнему регистру для надежности
        "DAY_MONDAY", "MONDAY" -> DayOfWeek.MONDAY
        "DAY_TUESDAY", "TUESDAY" -> DayOfWeek.TUESDAY
        "DAY_WEDNESDAY", "WEDNESDAY" -> DayOfWeek.WEDNESDAY
        "DAY_THURSDAY", "THURSDAY" -> DayOfWeek.THURSDAY
        "DAY_FRIDAY", "FRIDAY" -> DayOfWeek.FRIDAY
        "DAY_SATURDAY", "SATURDAY" -> DayOfWeek.SATURDAY
        "DAY_SUNDAY", "SUNDAY" -> DayOfWeek.SUNDAY
        else -> null // Если имя не распознано
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
class DynamicSelectableDates(
    private val availableDaysOfWeek: Set<DayOfWeek>
) : SelectableDates {

    private val currentYear = Year.now()
    private val today = LocalDate.now(ZoneId.systemDefault())
    private val tomorrow = today.plusDays(1)


    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        val date = Instant.ofEpochMilli(utcTimeMillis)
            .atZone(ZoneId.of("UTC")) // DatePicker обычно работает с UTC
            .toLocalDate()

        // 1. Дата должна быть не раньше завтрашнего дня
        if (date.isBefore(tomorrow)) {
            return false
        }

        // 2. Год даты должен быть текущим годом
        if (date.year != currentYear.value) {
            return false
        }

        // 3. День недели должен быть одним из доступных
        return date.dayOfWeek in availableDaysOfWeek
    }

    // Опционально: можно ограничить выбор годов, если это необходимо
    // override fun isSelectableYear(year: Int): Boolean {
    //     return year == currentYear.value
    // }
}
//endregion

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookingUI(
    serviceId: Int,
    userId: Int,
    onBookButtonClick: (date: String, time: String) -> Unit,
    onBackButtonClick: () -> Unit,
    viewModel: BookingViewModel = hiltViewModel()
) {
    LaunchedEffect(serviceId, userId) {
        viewModel.getTimeTable(userId)
    }

    val slots by viewModel.slotsFLow.collectAsState()
    val timeTable by viewModel.timeTableFlow.collectAsState()

    val selectableDatesLogic = remember(timeTable) {
        val apiDays = timeTable // Получаем список объектов Day из вашего API
        if (apiDays.isEmpty()) {
            DynamicSelectableDates(emptySet())
        } else {
            val availableDaysOfWeek = apiDays.mapNotNull { day ->
                mapApiDayNameToDayOfWeek(day.code)
            }.toSet()
            DynamicSelectableDates(availableDaysOfWeek)
        }
    }

    var isDatePickerVisible by remember { mutableStateOf(true) }
    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Picker,
        selectableDates = selectableDatesLogic
    )

    val selectedDate = datePickerState.selectedDateMillis?.let {
        viewModel.getSlots(serviceId, convertMillisToDate(it))
        isDatePickerVisible = false
        convertMillisToDate(it)
    } ?: LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))


    Box(
        modifier = Modifier.padding(all = 15.dp),
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            stickyHeader {
                SheetStickyHeader(
                    title = "Запись",
                    isBackButton = false
                )
            }
            item {
                ServiceTextField(
                    label = "Выберите день",
                    value = datePickerState.selectedDateMillis?.let { convertMillisToDate(it) }?: "",
                    onValueChange = {},
                    isReadOnly = true,
                    modifier = Modifier.pointerInput(selectedDate) {
                        awaitEachGesture {
                            awaitFirstDown(pass = PointerEventPass.Initial)
                            isDatePickerVisible = true
                        }
                    }
                )
            }
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    AnimatedVisibility(
                        visible = isDatePickerVisible,
                        enter = scaleIn() + fadeIn(),
                        exit = scaleOut() + fadeOut()
                    ) {
                        DatePicker(
                            modifier = Modifier.fillMaxWidth(),
                            state = datePickerState,
                            showModeToggle = false,
                            colors = DatePickerDefaults.colors(
                                containerColor = Color.Transparent,
                                dividerColor = Color.Transparent
                            ),
                            headline = null,
                            title = null
                        )
                    }
                    AnimatedVisibility(
                        visible = !isDatePickerVisible && selectedDate.isNotEmpty(),
                        enter = scaleIn() + fadeIn(),
                        exit = scaleOut() + fadeOut()
                    ) {
                        val groupedSlots: Map<TimeOfDayCategory, List<String>> = remember(slots) {
                            slots.groupBy { getTimeOfDayCategory(it) }
                        }

                        val displayOrder = listOf(
                            TimeOfDayCategory.MORNING,
                            TimeOfDayCategory.DAY,
                            TimeOfDayCategory.EVENING,
                            TimeOfDayCategory.NIGHT
                        )
                        Column {
                            var selectedTimeSlotState = viewModel.selectedTimeSlotState
                            displayOrder.forEach { category ->
                                val timesInCategory = groupedSlots[category]
                                if (!timesInCategory.isNullOrEmpty()) {
                                    Text(
                                        text = category.displayName,
                                        fontSize = 18.sp,
                                        modifier = Modifier
                                    )
                                    FlowRow(
                                        verticalArrangement = Arrangement.spacedBy((-10).dp),
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        timesInCategory.forEach { timeSlot ->
                                            FilterChip(
                                                selected = selectedTimeSlotState == timeSlot, // Ваша логика выбора
                                                onClick = {
                                                    viewModel.selectedTimeSlotState = if (selectedTimeSlotState == timeSlot) {
                                                        null
                                                    } else {
                                                        timeSlot
                                                    }
                                                    viewModel.date = selectedDate
                                                },
                                                label = { Text(timeSlot) },
                                                modifier = Modifier,
                                                colors = FilterChipDefaults.filterChipColors(
                                                    selectedContainerColor = MaterialTheme.colorScheme.primary
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            item {
                Button(
                    onClick = {
                        onBookButtonClick(
                            viewModel.date?: "",
                            viewModel.selectedTimeSlotState?: ""
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors()
                ) {
                    Text("Забронировать", fontSize = 20.sp)
                }
            }

        }
    }
    DisposableEffect(Unit) {
        onDispose {
            isDatePickerVisible = true
            viewModel.clearData()
        }
    }
}