package com.example.sellingserviceapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.sellingserviceapp.data.local.dao.CategoriesDao
import com.example.sellingserviceapp.data.local.dao.ServiceDao
import com.example.sellingserviceapp.data.local.dao.UserDao
import com.example.sellingserviceapp.data.local.entity.CategoryEntity
import com.example.sellingserviceapp.data.local.entity.FormatsEntity
import com.example.sellingserviceapp.data.local.entity.ServiceEntity
import com.example.sellingserviceapp.data.local.entity.SubcategoryEntity
import com.example.sellingserviceapp.data.local.entity.UserEntity
import com.example.sellingserviceapp.domain.converter.FormatsConverters

@Database(
    entities = [UserEntity::class, CategoryEntity::class, SubcategoryEntity::class, ServiceEntity::class, FormatsEntity::class],
    version = 6,
    exportSchema = false,
)
@TypeConverters(FormatsConverters::class)
abstract class AppDataBase: RoomDatabase() {
    abstract fun UserDao(): UserDao
    abstract fun CategoriesDao(): CategoriesDao
    abstract fun ServiceDao(): ServiceDao
}