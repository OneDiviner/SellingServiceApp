package com.example.sellingserviceapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("formats")
data class FormatsEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("code") val code: String,
    @ColumnInfo("is_physical") val isPhysical: Boolean
) {

}