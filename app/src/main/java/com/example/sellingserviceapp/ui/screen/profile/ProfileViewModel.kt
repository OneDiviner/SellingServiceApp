package com.example.sellingserviceapp.ui.screen.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.di.GlobalAppState
import com.example.sellingserviceapp.data.local.UserDataStorage
import com.example.sellingserviceapp.data.local.UserData
import com.example.sellingserviceapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userDataStorage: UserDataStorage,
    private val globalAppState: GlobalAppState
): ViewModel() {

    var profileState by mutableStateOf<ProfileState>(ProfileState.Loading)
        private set

    val userFlow: Flow<UserData> = userDataStorage.getUser().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        UserData("","", "", "", "")
    )

    var firstName by mutableStateOf("")
        private set
    var middleName by mutableStateOf("")
        private set

    private val _photoBase64 = mutableStateOf<String?>(null)
    val photoBase64: String? get() = _photoBase64.value

    init {
        viewModelScope.launch {
            profileState = ProfileState.Loading
            userDataStorage.updateUserData()
            userDataStorage.getUser().collect { userData ->
                _photoBase64.value = userData.avatarBase64
                profileState = ProfileState.Success
            }
        }
    }

}