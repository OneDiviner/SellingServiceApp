package com.example.sellingserviceapp.ui.screen.main

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import kotlinx.coroutines.Job
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
        private const val INITIAL_PAGE = 0
        const val SIZE = 6
    }

    private val _userFlow = MutableStateFlow<UserDomain>(UserDomain.EMPTY)
    val userFLow: StateFlow<UserDomain> = _userFlow.asStateFlow()

    private val _servicesFlow = MutableStateFlow<List<ServiceDomain>>(emptyList())
    val servicesFlow: StateFlow<List<ServiceDomain>> = _servicesFlow.asStateFlow()

    var categories by mutableStateOf<List<CategoryDomain>>(emptyList())
    var isRefreshing by mutableStateOf(false)
    var isFilterSelected by mutableStateOf<Int?>(null)
    var mainUiState by mutableStateOf<MainUIState>(MainUIState.Init)

    var currentPage by mutableIntStateOf(INITIAL_PAGE)
    var maxPage by mutableIntStateOf(0)
    var isLoadingMore by mutableStateOf(false)

    private val _searchQuery = MutableStateFlow<String?>(null)
    val searchQuery: StateFlow<String?> = _searchQuery

    private var currentLoadingJob: Job? = null


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
            val response = dataManager.requestServices(page = INITIAL_PAGE, size = SIZE)
            _servicesFlow.value = response.services ?: emptyList()
            currentPage = response.pageable.page
            maxPage = response.pageable.pageMax
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
            val response = dataManager.requestServices(page = INITIAL_PAGE, size = SIZE, categoryId =  isFilterSelected, title = _searchQuery.value)
            _servicesFlow.value = response.services ?: emptyList()
            currentPage = 0
            maxPage = response.pageable.pageMax
            isRefreshing = false
        }
    }

    fun loadMore() {
        currentLoadingJob?.cancel()
        isLoadingMore = true
        currentLoadingJob = viewModelScope.launch {
            if (currentPage <= maxPage) {
                currentPage ++
                val response = dataManager.requestServices(currentPage, SIZE, isFilterSelected, _searchQuery.value)
                val newServices = response.services ?: emptyList()
                _servicesFlow.value = _servicesFlow.value + newServices
                maxPage = response.pageable.pageMax

                isLoadingMore = false
            }
        }
        Log.d("LOAD_MORE","PAGE: $currentPage MAX_PAGE: $maxPage")
    }
}