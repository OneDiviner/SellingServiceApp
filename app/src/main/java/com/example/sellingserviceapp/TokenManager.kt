package com.example.sellingserviceapp

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class TokenManager(private val context: Context) {

    companion object {
        val USER_TOKEN = stringPreferencesKey("user_token")
    }

    //Save
    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_TOKEN] = token
        }
    }

    // Get
    val getToken: Flow<String?>
        get() = context.dataStore.data.map { preferences ->
            preferences[USER_TOKEN]
        }

    // Delete
    suspend fun clearToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_TOKEN)
        }
    }

    suspend fun isTokenExist(): Boolean {
        return context.dataStore.data.map { preferences ->
            preferences[USER_TOKEN] != null
        }.first()
    }

}