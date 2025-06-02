package com.example.sellingserviceapp.model.mapper

import com.example.sellingserviceapp.data.network.booking.Day
import com.example.sellingserviceapp.model.dto.CategoryDto
import com.example.sellingserviceapp.model.entity.CategoryEntity
import com.example.sellingserviceapp.model.mapper.DayMapper.codeToName

private val dayMap = mapOf(
    "DAY_MONDAY" to "ПН",
    "DAY_TUESDAY" to "ВТ",
    "DAY_WEDNESDAY" to "СР",
    "DAY_THURSDAY" to "ЧТ",
    "DAY_FRIDAY" to "ПТ",
    "DAY_SATURDAY" to "СБ",
    "DAY_SUNDAY" to "ВС"
)

object DayMapper {
    fun Day.codeToName(): Day {
        val name = dayMap[this.code]?: this.code
        return this.copy(name = name)
    }
}

fun daysListCodeToName(days: List<Day>): List<Day> {
    return days.map { it.codeToName() }
}