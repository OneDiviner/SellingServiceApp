package com.example.sellingserviceapp.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("formats")
data class FormatsEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo("code") val code: String,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("address") val address: String?,
    @ColumnInfo("is_physical") val isPhysical: Boolean
)