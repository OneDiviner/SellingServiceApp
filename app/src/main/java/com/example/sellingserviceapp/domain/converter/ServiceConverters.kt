package com.example.sellingserviceapp.domain.converter

import androidx.room.TypeConverter
import com.example.sellingserviceapp.data.local.entity.FormatsEntity
import com.example.sellingserviceapp.data.local.entity.ServiceEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object FormatsConverters {
    private val gson = Gson()
    private val type = object : TypeToken<List<FormatsEntity>>() {}.type

    @TypeConverter
    fun toJson(formats: List<FormatsEntity>): String {
        return gson.toJson(formats)
    }

    @TypeConverter
    fun toFormatsList(json: String?): List<FormatsEntity>? {
        return json?.takeIf { it.isNotBlank() }?.let {
            gson.fromJson(it, type)
        }
    }
}