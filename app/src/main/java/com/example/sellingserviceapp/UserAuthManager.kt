package com.example.sellingserviceapp

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

data class UserAuthData(
    val email: String,
    val token: String
)

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class UserAuthManager(private val context: Context) {

    /*companion object {
        val ACCESS_TOKEN = stringPreferencesKey("user_token")
    }*/

    companion object {
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_TOKEN = stringPreferencesKey("user_token")
    }

    // Сохранить токен и почту
    suspend fun saveAuthData(email: String, token: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_EMAIL] = email
            preferences[USER_TOKEN] = token
        }
    }

    suspend fun getEmail(): String? {
        return context.dataStore.data.map { preferences ->
            preferences[USER_EMAIL]
        }.first()
    }

    suspend fun getToken(): String? {
        return context.dataStore.data.map { preferences ->
            preferences[USER_TOKEN]
        }.first()
    }

    suspend fun isTokenValidForEmail(email: String): Boolean {
        val savedEmail = getEmail()
        val savedToken = getToken()
        return savedEmail == email && !savedToken.isNullOrEmpty()
    }

    suspend fun clearAuthData() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_EMAIL)
            preferences.remove(USER_TOKEN)
        }
    }

    /*//Save
    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = token
        }
    }

    // Get
    val getToken: Flow<String?>
        get() = context.dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN]
        }

    // Delete
    suspend fun clearToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN)
        }
    }

    suspend fun isTokenExist(): Boolean {
        return context.dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN] != null
        }.first()
    }*/

}