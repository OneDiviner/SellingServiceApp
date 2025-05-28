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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    var serviceList by mutableStateOf<List<ServiceDomain>>(emptyList())
    var carRepairList by mutableStateOf<List<ServiceDomain>>(emptyList())
    var isSheetOpen by mutableStateOf(false)
    //var sheetContentState by mutableStateOf<SheetContentState>(SheetContentState.Default)
    var isRefreshing by mutableStateOf(false)

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
        viewModelScope.launch {
            _servicesFlow.value = dataManager.requestServices(page = PAGE, size = SIZE)
            val filteredList = serviceList.filter { it.subcategoryCode == "SUBCATEGORY_REPAIR_CAR" }
            carRepairList = filteredList
            isRefreshing = false
            categories = dataManager.getCategories()
        }
    }

    fun getServiceList() {
        isRefreshing = true
        viewModelScope.launch {
            _servicesFlow.value = dataManager.requestServices(page = PAGE, size = SIZE)
            val filteredList = serviceList.filter { it.subcategoryCode == "SUBCATEGORY_REPAIR_CAR" }
            carRepairList = filteredList
            isRefreshing = false
        }
    }

    fun getServiceListByCategory(categoryId: Int) {
        isRefreshing = true
        viewModelScope.launch {
            _servicesFlow.value = dataManager.fetchServicesByCategory(page = PAGE, size = SIZE, categoryId)
            val filteredList = serviceList.filter { it.subcategoryCode == "SUBCATEGORY_REPAIR_CAR" }
            carRepairList = filteredList
            isRefreshing = false
        }
    }
}