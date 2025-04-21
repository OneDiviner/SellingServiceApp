package com.example.sellingserviceapp.ui.screen.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.local.UserDataStore
import com.example.sellingserviceapp.data.local.UserName
import com.example.sellingserviceapp.data.network.AuthApiService
import com.example.sellingserviceapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FullName(
    var firstName: String = "",
    var middleName: String = "",
    var lastName: String = ""
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userDataStore: UserDataStore
): ViewModel() {

    /*val userFlow: Flow<UserName> = userDataStore.getUser().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        UserName("","", "")
    )*/ //TODO: Сделать безопасное хранилище и обычное для хранения ФИО, телефона, почты

    var firstName by mutableStateOf("")
        private set
    var middleName by mutableStateOf("")
        private set

    private val _photoBase64 = mutableStateOf<String?>(null)
    val photoBase64: String? get() = _photoBase64.value

    init {
        viewModelScope.launch {
            val result = authRepository.getUser()
            result.onSuccess { success ->
                firstName = success.user.firstName
                middleName = success.user.middleName
                _photoBase64.value = success.user.avatar
                /*userDataStore.saveUser(
                    user = UserName(
                        firstName = success.user.firstName,
                        middleName = success.user.middleName,
                        lastName = success.user.lastName
                    )
                )*/ //TODO: См. TODO выше
            }.onFailure {

            }
        }
    }

}