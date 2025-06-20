package com.example.sellingserviceapp.ui.screen.createService.newService

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.manager.DataManager
import com.example.sellingserviceapp.model.domain.CategoryDomain
import com.example.sellingserviceapp.model.domain.FormatsDomain
import com.example.sellingserviceapp.model.domain.NewServiceDomain
import com.example.sellingserviceapp.model.domain.PriceTypeDomain
import com.example.sellingserviceapp.model.domain.SubcategoryDomain
import com.example.sellingserviceapp.ui.screen.createService.newService.NewServiceValidators.validatePrice
import com.example.sellingserviceapp.ui.screen.createService.newService.NewServiceValidators.validateTitle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewServiceViewModel @Inject constructor(
    private val dataManager: DataManager
): ViewModel() {

    var newServiceUIState by mutableStateOf<NewServiceUIState>(NewServiceUIState.Categories)
    var categories by mutableStateOf<List<CategoryDomain>>(emptyList())
    var subcategories by mutableStateOf<List<SubcategoryDomain>>(emptyList())
    var formats by mutableStateOf<List<FormatsDomain>>(emptyList())
    var priceTypes by mutableStateOf<List<PriceTypeDomain>>(emptyList())

    var newService by mutableStateOf<NewServiceDomain>(NewServiceDomain.EMPTY)

    var error = MutableStateFlow<String?>(null)

    var hour by mutableStateOf("00")
    var minute by mutableStateOf("15")
    var duration by mutableIntStateOf(15)

    init {
        viewModelScope.launch {
            categories = dataManager.getCategories()
            formats = dataManager.getFormats()
            priceTypes = dataManager.getPriceTypes()
            newService = newService.copy(
                tittle = "",
                description = "",
                duration = "",
                address = "",
                price = "",
                priceTypeId = priceTypes[0].id,
                formatsIds = emptyList()
            )
        }
    }

    fun getSubcategories(categoryId: Int) {
        viewModelScope.launch {
            dataManager.requestSubcategories(categoryId)
            subcategories = dataManager.getSubcategories(categoryId)
        }
    }

    fun selectFormats(formats: List<Int>) {
        newService = newService.copy(formatsIds = formats)
    }

    fun createService() {
        viewModelScope.launch {
            dataManager.createService(newService = newService)
        }
    }

    fun onTittleChanged(value: String) {
        newService = newService.copy(tittle = value)
        error.value = newService.validateTitle()
    }

    fun onPriceChanged(value: String) {
        newService = newService.copy(price = value)
        error.value = newService.validatePrice()
    }
}