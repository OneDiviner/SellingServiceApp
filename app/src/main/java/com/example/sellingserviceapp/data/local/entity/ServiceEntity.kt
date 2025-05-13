package com.example.sellingserviceapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.sellingserviceapp.domain.converter.FormatsConverters

@Entity(
    tableName = "services",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ServiceEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo("user_id") val userId: Int,
    @ColumnInfo("tittle") val tittle: String,
    @ColumnInfo("description") val description: String?,
    @ColumnInfo("duration") val duration: Int?,
    @ColumnInfo("photo_path") val photoPath: String,
    @ColumnInfo("photo") val photo: String?,
    @ColumnInfo("price") val price: Int?,
    @ColumnInfo("created_at") val createdAt: String?,
    @ColumnInfo("updated_at") val updatedAt: String?,
    @ColumnInfo("price_type") val priceType: String?,
    @ColumnInfo("status") val status: String,
    @TypeConverters(FormatsConverters::class) @ColumnInfo("formats") val formats: List<FormatsEntity>?,
    @ColumnInfo("category") val category: String,
    @ColumnInfo("subcategory") val subcategory: String
)