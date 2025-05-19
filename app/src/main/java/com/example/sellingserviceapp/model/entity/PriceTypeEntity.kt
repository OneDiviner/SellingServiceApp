package com.example.sellingserviceapp.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("price_types")
data class PriceTypeEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("code") val code: String
)