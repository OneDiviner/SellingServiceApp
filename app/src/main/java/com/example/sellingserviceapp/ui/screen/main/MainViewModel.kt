package com.example.sellingserviceapp.ui.screen.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.DataManager
import com.example.sellingserviceapp.model.domain.CategoryDomain
import com.example.sellingserviceapp.model.domain.ServiceDomain
import com.example.sellingserviceapp.model.domain.UserDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataManager: DataManager
): ViewModel() {

    companion object {
        private const val PAGE = 0
        const val SIZE = 20
    }

    private val _userFlow = MutableStateFlow<UserDomain>(UserDomain.EMPTY)
    val userFLow: StateFlow<UserDomain> = _userFlow.asStateFlow()

    private val _servicesFlow = MutableStateFlow<List<ServiceDomain>>(emptyList())
    val servicesFlow: StateFlow<List<ServiceDomain>> = _servicesFlow.asStateFlow()

    var categories by mutableStateOf<List<CategoryDomain>>(emptyList())

    var isRefreshing by mutableStateOf(false)

    var mainUiState by mutableStateOf<MainUIState>(MainUIState.Init)

    init {
        init()
        getUser()
    }

    fun getUser() {
        viewModelScope.launch {
            dataManager.getUserFlow().collect { user ->
                _userFlow.value = user
            }
        }
    }

    fun init() {
        isRefreshing = true
        mainUiState = MainUIState.Init
        viewModelScope.launch {
            _servicesFlow.value = dataManager.requestServices(page = PAGE, size = SIZE)

            isRefreshing = false
            categories = dataManager.getCategories()
            mainUiState = MainUIState.Loaded
        }
    }

    fun getServiceList() {
        isRefreshing = true
        viewModelScope.launch {
            _servicesFlow.value = dataManager.requestServices(page = PAGE, size = SIZE)


            isRefreshing = false
        }
    }

    fun getServiceListByCategory(categoryId: Int) {
        isRefreshing = true
        viewModelScope.launch {
            _servicesFlow.value = dataManager.fetchServicesByCategory(page = PAGE, size = SIZE, categoryId)


            isRefreshing = false
        }
    }

    fun refreshMainScreen() {
        viewModelScope.launch {
            isRefreshing = true
            viewModelScope.launch {
                _servicesFlow.value = dataManager.requestServices(page = PAGE, size = SIZE)
                categories = dataManager.getCategories()
                isRefreshing = false
            }
        }
    }
}