package com.example.sellingserviceapp.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import javax.inject.Singleton

class SecureTokenStorage private constructor(context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: SecureTokenStorage? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(context: Context): SecureTokenStorage {
            return INSTANCE?: synchronized(lock = this) {
                INSTANCE?: SecureTokenStorage(context = context.applicationContext).also { INSTANCE = it }
            }
        }
    }

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secure_token_storage",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    // Сохранение токенов
    fun saveTokens(accessToken: String, refreshToken: String) {
        sharedPreferences.edit()
            .putString("access_token", accessToken)
            .putString("refresh_token", refreshToken)
            .apply()
    }

    // Получение access token
    fun getAccessToken(): String? {
        return sharedPreferences.getString("access_token", null)
    }

    // Получение refresh token
    fun getRefreshToken(): String? {
        return sharedPreferences.getString("refresh_token", null)
    }

    // Обновление access token
    fun updateAccessToken(newToken: String) {
        sharedPreferences.edit()
            .putString("access_token", newToken)
            .apply()
    }

    // Удаление всех токенов (для logout)
    fun clearTokens() {
        sharedPreferences.edit()
            .remove("access_token")
            .remove("refresh_token")
            .apply()
    }

    // Проверка наличия токенов
    fun hasTokens(): Boolean {
        return !getAccessToken().isNullOrEmpty() && !getRefreshToken().isNullOrEmpty()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object SecurityModule {
    @Provides
    @Singleton
    fun provideSecureTokenStorage(@ApplicationContext context: Context): SecureTokenStorage {
        return SecureTokenStorage.getInstance(context)
    }
}