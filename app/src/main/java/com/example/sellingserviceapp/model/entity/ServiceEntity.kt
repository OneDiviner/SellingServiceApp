package com.example.sellingserviceapp.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.sellingserviceapp.model.mapper.FormatsConverters

@Entity(
    tableName = "services",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["id"],
        childColumns = ["user_id"],
        //onDelete = ForeignKey.CASCADE
    )]
)
data class ServiceEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo("user_id") val userId: Int,
    @ColumnInfo("tittle") val tittle: String,
    @ColumnInfo("description") val description: String?,
    @ColumnInfo("duration") val duration: Int?,
    @ColumnInfo("photo_path") val photoPath: String?,
    @ColumnInfo("photo") val photo: String?,
    @ColumnInfo("price") val price: Int?,
    @ColumnInfo("created_at") val createdAt: String?,
    @ColumnInfo("updated_at") val updatedAt: String?,
    @ColumnInfo("price_type_name") val priceTypeName: String?,
    @ColumnInfo("price_type_code") val priceTypeCode: String?,
    @ColumnInfo("status_name") val statusName: String,
    @ColumnInfo("status_code") val statusCode: String,
    @TypeConverters(FormatsConverters::class) @ColumnInfo("formats") val formats: List<FormatsEntity>?,
    @ColumnInfo("category_name") val categoryName: String,
    @ColumnInfo("category_code") val categoryCode: String,
    @ColumnInfo("subcategory_name") val subcategoryName: String,
    @ColumnInfo("subcategory_code") val subcategoryCode: String
)