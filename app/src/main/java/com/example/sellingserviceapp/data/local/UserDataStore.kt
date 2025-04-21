package com.example.sellingserviceapp.data.local

import android.content.Context
import androidx.core.content.pm.PermissionInfoCompat.ProtectionFlags
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.sellingserviceapp.data.model.response.User
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

data class UserName(
    var firstName: String,
    var middleName: String,
    var lastName: String
)

class UserDataStore(
    private val context: Context
) {
    companion object {
        val FIRST_NAME = stringPreferencesKey("first_name")
        val MIDDLE_NAME = stringPreferencesKey("middle_name")
        val LAST_NAME = stringPreferencesKey("last_name")
       // val PHOTO_URL = stringPreferencesKey("photo_url")
    }

    suspend fun saveUser(user: UserName) {
        context.dataStore.edit { prefs ->
            prefs[FIRST_NAME] = user.firstName
            prefs[MIDDLE_NAME] = user.middleName
            prefs[LAST_NAME] = user.lastName // Пометить как Null
            //prefs[PHOTO_URL] = user.avatar
        }
    }

    fun getUser(): Flow<UserName> {
        return context.dataStore.data.map { prefs ->
            UserName(
                firstName = prefs[FIRST_NAME] ?: "",
                middleName =  prefs[MIDDLE_NAME] ?: "",
                lastName = prefs[LAST_NAME] ?: ""
            )
        }
    }

    suspend fun clearUserData() {
        context.dataStore.edit { it.clear() }
    }

}

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideUserDataStore(@ApplicationContext context: Context): UserDataStore {
        return UserDataStore(context)
    }
}