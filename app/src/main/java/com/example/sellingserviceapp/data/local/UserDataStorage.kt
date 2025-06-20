package com.example.sellingserviceapp.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.sellingserviceapp.data.network.authorization.repository.AuthRepository
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