package com.example.sellingserviceapp.ui.screen.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.manager.DataManager
import com.example.sellingserviceapp.model.domain.CategoryDomain
import com.example.sellingserviceapp.model.domain.ServiceDomain
import com.example.sellingserviceapp.model.domain.UserDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
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
    var isFilterSelected by mutableStateOf<Int?>(null)
    var mainUiState by mutableStateOf<MainUIState>(MainUIState.Init)

    private val _searchQuery = MutableStateFlow<String?>(null)
    val searchQuery: StateFlow<String?> = _searchQuery

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
            categories = dataManager.getCategories()
            mainUiState = MainUIState.Loaded
            isRefreshing = false
        }
        getServiceListByTittle()
    }

    fun onSearchChanged(value: String) {
        _searchQuery.value = value
    }

    @OptIn(FlowPreview::class)
    fun getServiceListByTittle() {
        viewModelScope.launch {
            searchQuery
                .debounce(500)
                .distinctUntilChanged()
                .collect { query ->
                    if (_searchQuery.value == "") {
                        _searchQuery.value = null
                    }
                    searchService()
                }
        }
    }

    fun searchService() {
        isRefreshing = true
        viewModelScope.launch {
            _servicesFlow.value = dataManager.requestServices(page = PAGE, size = SIZE, categoryId =  isFilterSelected, title = _searchQuery.value)
            isRefreshing = false
        }
    }
}