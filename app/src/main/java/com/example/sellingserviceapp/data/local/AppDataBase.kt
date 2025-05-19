package com.example.sellingserviceapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.sellingserviceapp.data.local.dao.CategoriesDao
import com.example.sellingserviceapp.data.local.dao.FormatsDao
import com.example.sellingserviceapp.data.local.dao.PriceTypeDao
import com.example.sellingserviceapp.data.local.dao.ServiceDao
import com.example.sellingserviceapp.data.local.dao.SubcategoriesDao
import com.example.sellingserviceapp.data.local.dao.UserDao
import com.example.sellingserviceapp.model.entity.CategoryEntity
import com.example.sellingserviceapp.model.entity.FormatsEntity
import com.example.sellingserviceapp.model.entity.PriceTypeEntity
import com.example.sellingserviceapp.model.entity.ServiceEntity
import com.example.sellingserviceapp.model.entity.SubcategoryEntity
import com.example.sellingserviceapp.model.entity.UserEntity
import com.example.sellingserviceapp.model.mapper.FormatsConverters

@Database(
    entities = [UserEntity::class, CategoryEntity::class, SubcategoryEntity::class, ServiceEntity::class, FormatsEntity::class, PriceTypeEntity::class],
    version = 15,
    exportSchema = false,
)
@TypeConverters(FormatsConverters::class)
abstract class AppDataBase: RoomDatabase() {
    abstract fun UserDao(): UserDao
    abstract fun CategoriesDao(): CategoriesDao
    abstract fun ServiceDao(): ServiceDao
    abstract fun FormatsDao(): FormatsDao
    abstract fun PriceTypeDao(): PriceTypeDao
    abstract fun SubcategoriesDao(): SubcategoriesDao
}