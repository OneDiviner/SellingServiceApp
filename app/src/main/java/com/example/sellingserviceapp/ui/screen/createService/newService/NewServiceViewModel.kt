package com.example.sellingserviceapp.ui.screen.createService.newService

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.DataManager
import com.example.sellingserviceapp.data.network.offer.request.CreateServiceRequest
import com.example.sellingserviceapp.model.domain.CategoryDomain
import com.example.sellingserviceapp.model.domain.FormatsDomain
import com.example.sellingserviceapp.model.domain.NewServiceDomain
import com.example.sellingserviceapp.model.domain.PriceTypeDomain
import com.example.sellingserviceapp.model.domain.ServiceDomain
import com.example.sellingserviceapp.model.domain.SubcategoryDomain
import dagger.hilt.android.lifecycle.HiltViewModel
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

    init {
        viewModelScope.launch {
            categories = dataManager.getCategories()
        }
        viewModelScope.launch {
            formats = dataManager.getFormats()
        }
        viewModelScope.launch {
            priceTypes = dataManager.getPriceTypes()
        }
    }

    fun getSubcategories(categoryId: Int) {
        viewModelScope.launch {
            dataManager.requestSubcategories(categoryId)
            subcategories = dataManager.getSubcategories(categoryId)
        }
    }

    fun createService() {
        viewModelScope.launch {

        }
    }
}