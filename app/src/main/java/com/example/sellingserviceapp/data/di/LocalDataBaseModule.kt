package com.example.sellingserviceapp.data.di

import android.content.Context
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.sellingserviceapp.data.local.AppDataBase
import com.example.sellingserviceapp.data.local.dao.CategoriesDao
import com.example.sellingserviceapp.data.local.dao.ServiceDao
import com.example.sellingserviceapp.data.local.dao.UserDao
import com.example.sellingserviceapp.data.local.migration.MIGRATION_1_2
import com.example.sellingserviceapp.data.local.migration.MIGRATION_2_3
import com.example.sellingserviceapp.data.local.migration.MIGRATION_3_4
import com.example.sellingserviceapp.data.local.migration.MIGRATION_4_5
import com.example.sellingserviceapp.data.local.migration.MIGRATION_5_6
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataBaseModule {

    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(
                context,
                AppDataBase::class.java,
                "app-database"
            )
            .fallbackToDestructiveMigration(true)
            .setJournalMode(RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING)
            .build()
    }

    @Provides
    fun provideUserDao(dataBase: AppDataBase): UserDao {
        return dataBase.UserDao()
    }

    @Provides
    fun provideCategoriesDao(dataBase: AppDataBase): CategoriesDao {
        return dataBase.CategoriesDao()
    }

    @Provides
    fun provideServiceDap(dataBase: AppDataBase): ServiceDao {
        return dataBase.ServiceDao()
    }
}