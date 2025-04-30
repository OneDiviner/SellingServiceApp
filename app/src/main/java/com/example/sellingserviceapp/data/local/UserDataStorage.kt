package com.example.sellingserviceapp.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.sellingserviceapp.data.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
//TODO: Подумать над использованием dataStore для хранения глобального состояния приложения
private val Context.dataStore by preferencesDataStore(name = "user_prefs")

data class UserData(
    var firstName: String,
    var middleName: String,
    var lastName: String?,
    var email: String, //TODO: Обязательно вынести в защищенный модуль
    var avatarBase64: String?
)

class UserDataStorage @Inject constructor(
    private val context: Context,
    private val authRepository: AuthRepository
) {

    companion object {
        val FIRST_NAME = stringPreferencesKey("first_name")
        val MIDDLE_NAME = stringPreferencesKey("middle_name")
        val LAST_NAME = stringPreferencesKey("last_name")
        val EMAIL = stringPreferencesKey("email")
        val AVATAR_BASE64 = stringPreferencesKey("avatar_base64")
    }

    suspend fun saveUser(userData: UserData) {
        context.dataStore.edit { prefs ->
            prefs[FIRST_NAME] = userData.firstName
            prefs[MIDDLE_NAME] = userData.middleName
            prefs[LAST_NAME] = userData.lastName?: ""
            prefs[EMAIL] = userData.email
            prefs[AVATAR_BASE64] = userData.avatarBase64?: ""
        }
    }

    suspend fun updateUserData() {
        coroutineScope {
            Log.d("DATA_STORAGE", "GET_USER")
            val user = authRepository.getUser()
            user.onSuccess { success ->
                context.dataStore.edit { prefs ->
                    prefs[FIRST_NAME] = success.user.firstName
                    prefs[MIDDLE_NAME] = success.user.middleName
                    prefs[LAST_NAME] = success.user.lastName // Пометить как Null
                    prefs[EMAIL] = success.user.email
                    prefs[AVATAR_BASE64] = success.user.avatar
                }
            }
        }
    }

    fun getUser(): Flow<UserData> {
        return context.dataStore.data.map { prefs ->
            UserData(
                firstName = prefs[FIRST_NAME] ?: "",
                middleName =  prefs[MIDDLE_NAME] ?: "",
                lastName = prefs[LAST_NAME] ?: "",
                email = prefs[EMAIL]?: "",
                avatarBase64 = prefs[AVATAR_BASE64]?: ""
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
    fun provideUserDataStore(
        authRepository: AuthRepository,
        @ApplicationContext context: Context
    ): UserDataStorage {
        return UserDataStorage(context, authRepository)
    }
}