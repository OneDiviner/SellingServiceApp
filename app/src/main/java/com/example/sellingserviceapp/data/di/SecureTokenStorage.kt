package com.example.sellingserviceapp.data.di

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
// import java.io.IOException // Для MasterKey
import java.security.GeneralSecurityException // Для EncryptedSharedPreferences и MasterKey
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
        private const val TAG = "SecureTokenStorage"
        const val PREFS_FILENAME = "secure_token_storage_prefs" // Изменено для ясности
        const val ACCESS_TOKEN_KEY = "access_token"
        const val REFRESH_TOKEN_KEY = "refresh_token"

        @Volatile
        private var INSTANCE: SecureTokenStorage? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(context: Context): SecureTokenStorage {
            return INSTANCE ?: synchronized(lock = this) {
                INSTANCE ?: SecureTokenStorage(context.applicationContext).also { INSTANCE = it }
            }
        }
    }

    private var masterKey: MasterKey
    private var sharedPreferences: SharedPreferences

    init {
        try {
            masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            sharedPreferences = EncryptedSharedPreferences.create(
                context,
                PREFS_FILENAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (e: GeneralSecurityException) {
            Log.e(TAG, "Error initializing EncryptedSharedPreferences. Deleting old prefs and retrying.", e)
            // Попытка удалить старые (возможно, поврежденные или зашифрованные старым ключом) SharedPreferences
            context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE).edit().clear().apply()
            // И повторная попытка инициализации (может снова выбросить исключение, если проблема не в старых данных)
            masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
            sharedPreferences = EncryptedSharedPreferences.create(
                context,
                PREFS_FILENAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
            // Если и здесь ошибка, приложение, вероятно, не сможет работать с токенами.
            // В продакшене здесь может потребоваться более сложная логика обработки.
        }
        // catch (e: IOException) { // MasterKey может также выбрасывать IOException
        //     Log.e(TAG, "IOException during MasterKey creation.", e)
        //     throw RuntimeException("Failed to initialize SecureTokenStorage due to MasterKey IOException", e)
        // }
    }


    fun saveTokens(accessToken: String, refreshToken: String) {
        try {
            sharedPreferences.edit()
                .putString(ACCESS_TOKEN_KEY, accessToken)
                .putString(REFRESH_TOKEN_KEY, refreshToken)
                .apply()
        } catch (e: GeneralSecurityException) {
            Log.e(TAG, "Error saving tokens", e)
            // Обработка ошибки, возможно, токены не сохранены
        }
    }

    fun getAccessToken(): String? {
        return try {
            sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
        } catch (e: GeneralSecurityException) {
            Log.e(TAG, "Error getting access token, returning null", e)
            // Если не удалось расшифровать/прочитать, считаем, что токена нет
            null
        }
    }

    fun getRefreshToken(): String? {
        return try {
            sharedPreferences.getString(REFRESH_TOKEN_KEY, null)
        } catch (e: GeneralSecurityException) {
            Log.e(TAG, "Error getting refresh token, returning null", e)
            null
        }
    }

    fun updateAccessToken(newToken: String) {
        try {
            sharedPreferences.edit()
                .putString(ACCESS_TOKEN_KEY, newToken)
                .apply()
        } catch (e: GeneralSecurityException) {
            Log.e(TAG, "Error updating access token", e)
        }
    }

    fun clearTokens() {
        try {
            sharedPreferences.edit()
                .remove(ACCESS_TOKEN_KEY)
                .remove(REFRESH_TOKEN_KEY)
                .apply()
        } catch (e: GeneralSecurityException) {
            Log.e(TAG, "Error clearing tokens", e)
        }
    }

    fun hasTokens(): Boolean {
        // getAccessToken() и getRefreshToken() уже обрабатывают ошибки
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