package com.example.sellingserviceapp.ui.screen.settings

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
//TODO: Разобраться от куда я буду получать данные о пользователе,
// т.к. БД будет несколько сделать разные репозитории для каждой БД
): ViewModel() {


    var userProfileState by mutableStateOf<UserProfileState>(UserProfileState.Loading)
        private set

    private val _avatarUri = MutableStateFlow<Uri?>(null)
    val avatarUri: StateFlow<Uri?> = _avatarUri

    var userName by mutableStateOf("Default")
        private set

    var userRating by mutableStateOf("No rating")
        private set

    init {
        getUserData()
        //TODO: Сделать запрос в бд на загрузку данных профиля
        Log.d("PROFILE_SCREEN", "WAS_INITIALIZED")
        loadProfile()
    }

    fun uriToFile(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "temp_file")
        inputStream?.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        return file
    }

    fun createMultipartBodyPart(file: File, partName: String): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            partName,
            file.name,
            file.asRequestBody("image/*".toMediaTypeOrNull())
        )
    }

    fun updateAvatar(context: Context, uri: Uri) {
        viewModelScope.launch {
            val file = uriToFile(context, uri)
            val multiPartFile = createMultipartBodyPart(file, "file")

            val result = authRepository.updateAvatar("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI1QG1haWwucnUiLCJyb2xlIjoiVXNlciIsInN0YXR1cyI6IkFjdGl2ZSIsInRva2VuX3R5cGUiOiJBY2Nlc3MiLCJpYXQiOjE3NDE3MDczNTgsImV4cCI6MTc0MTcwODI1OH0.iQL-3_-05Y9DaYr3QhpHSoEGf2Fn5HuK5gdoNm8A79X6zFmaRD6I9ZWQY0zc3QVtTNFBxbQaylw9shFQDUxuGw",file = multiPartFile)

            result.onSuccess { response ->
                Log.d("UPDATE_AVATAR", "AVATAR_DB_SAVE_SUCCESSFULLY, Message[${response.message}]")
            }.onFailure { error ->
                Log.d("UPDATE_AVATAR_ERROR_MESSAGE", "AVATAR_DB_SAVE_FAILURE, Error message[${error.message}]")
                Log.d("UPDATE_AVATAR_ERROR_MESSAGE", "AVATAR_DB_SAVE_FAILURE, Cause[${error.cause}]")
                Log.d("UPDATE_AVATAR_ERROR_MESSAGE", "AVATAR_DB_SAVE_FAILURE, Localized message[${error.localizedMessage}]")
                Log.d("UPDATE_AVATAR_ERROR_MESSAGE", "AVATAR_DB_SAVE_FAILURE, Stack trace[${error.stackTrace}]")
                Log.d("UPDATE_AVATAR_ERROR_MESSAGE", "AVATAR_DB_SAVE_FAILURE, Suppressed[${error.suppressed}]")
            }
        }
    }

    private fun getUserData() {
        viewModelScope.launch {
            Log.d("GET_USER_DATA", "REQUEST_SEND")
            val userData = authRepository.getUserData("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI1QG1haWwucnUiLCJyb2xlIjoiVXNlciIsInN0YXR1cyI6IkFjdGl2ZSIsInRva2VuX3R5cGUiOiJBY2Nlc3MiLCJpYXQiOjE3NDE3MDczNTgsImV4cCI6MTc0MTcwODI1OH0.iQL-3_-05Y9DaYr3QhpHSoEGf2Fn5HuK5gdoNm8A79X6zFmaRD6I9ZWQY0zc3QVtTNFBxbQaylw9shFQDUxuGw")
            userData.onSuccess { response ->
                Log.d("GET_USER_DATA", "SUCCESS")
                /*Log.d("GET_USER_DATA", "Email: ${response.user.email}")
                Log.d("GET_USER_DATA", "ID: ${response.user.id}")
                Log.d("GET_USER_DATA", "Name: ${response.user.firstName}")*/
            }.onFailure { error ->
                Log.d("GET_USER_DATA", "ERROR")
                Log.d("GET_USER_DATA", "AVATAR_DB_SAVE_FAILURE, Error message[${error.message}]")
                Log.d("GET_USER_DATA", "AVATAR_DB_SAVE_FAILURE, Cause[${error.cause}]")
                Log.d("GET_USER_DATA", "AVATAR_DB_SAVE_FAILURE, Localized message[${error.localizedMessage}]")
                Log.d("GET_USER_DATA", "AVATAR_DB_SAVE_FAILURE, Stack trace[${error.stackTrace}]")
                Log.d("GET_USER_DATA", "AVATAR_DB_SAVE_FAILURE, Suppressed[${error.suppressed}]")
            }
        }
    }

    fun loadProfile() {
        viewModelScope.launch {
            Log.d("LOAD_PROFILE", "STARTED")
            userProfileState = UserProfileState.Loading
            //val userData = profileRepository.loadUserData()
            delay(2000L)
            //OnSuccess
            userName = "Daniil Nikitin"
            userRating = "4,45"
            userProfileState = UserProfileState.Success
            Log.d("LOAD_PROFILE", "END_SUCCESSFULLY")
        }
    }

    override fun onCleared() {
        Log.d("PROFILE_SCREEN", "WAS_CLEARED")
        super.onCleared()
        //TODO: Сделать очистку кэша в репоизитрии
    }
}