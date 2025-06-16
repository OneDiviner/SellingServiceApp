package com.example.sellingserviceapp.ui.screen.createService.newService

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.manager.DataManager
import com.example.sellingserviceapp.model.domain.CategoryDomain
import com.example.sellingserviceapp.model.domain.FormatsDomain
import com.example.sellingserviceapp.model.domain.NewServiceDomain
import com.example.sellingserviceapp.model.domain.PriceTypeDomain
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
    var duration by mutableStateOf(
        listOf(
            "15",
            "30",
            "45",
            "60"
        )
    )
    var newService by mutableStateOf<NewServiceDomain>(NewServiceDomain.EMPTY)

    init {
        viewModelScope.launch {
            categories = dataManager.getCategories()
            formats = dataManager.getFormats()
            priceTypes = dataManager.getPriceTypes()
            newService = newService.copy(
                tittle = "",
                description = "",
                duration = duration[0],
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
            Log.d("NewServiceLog", "Title: ${newService.tittle}")
            Log.d("NewServiceLog", "Description: ${newService.description}")
            Log.d("NewServiceLog", "Duration: ${newService.duration}")
            Log.d("NewServiceLog", "Location: ${newService.address}")
            Log.d("NewServiceLog", "Price: ${newService.price}")
            Log.d("NewServiceLog", "Price Type ID: ${newService.priceTypeId}")
            Log.d("NewServiceLog", "Subcategory ID: ${newService.subcategoryId}")
            Log.d("NewServiceLog", "Formats ID: ${newService.formatsIds}")

            dataManager.createService(newService = newService)
        }
    }
}