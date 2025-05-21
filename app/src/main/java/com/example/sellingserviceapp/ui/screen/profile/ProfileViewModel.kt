package com.example.sellingserviceapp.ui.screen.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.DataManager
import com.example.sellingserviceapp.data.local.repository.user.UserRepository
import com.example.sellingserviceapp.data.network.authorization.repository.AuthRepository
import com.example.sellingserviceapp.model.domain.UserDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataManager: DataManager
): ViewModel() {

    var screenState by mutableStateOf<ScreenState>(ScreenState.Loading)
        private set
    var profileSheetState by mutableStateOf<ProfileSheetState>(ProfileSheetState.Profile)

    private val _userFlow = MutableStateFlow<UserDomain>(UserDomain.EMPTY)
    val userFLow: StateFlow<UserDomain> = _userFlow.asStateFlow()

    var newUserData by mutableStateOf<UserDomain>(UserDomain.EMPTY)
        private set

    init {
        viewModelScope.launch {
            screenState = ScreenState.Loading
            dataManager.getUser().collect{ user ->
                _userFlow.value = user
                screenState = ScreenState.Success
            }
        }
        viewModelScope.launch {
            dataManager.getUser().first().let { user->
                newUserData = user.copy()
            }
        }
    }


    fun onPhotoSelected(base64: String?) {
        viewModelScope.launch {
            dataManager.updateAvatar(base64?: "")
        }
    }



    fun logout() {
        viewModelScope.launch {
            dataManager.logout()
        }
    }

}