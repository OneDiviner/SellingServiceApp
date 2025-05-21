package com.example.sellingserviceapp.ui.screen.createService.service.editService

import android.view.View
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.DataManager
import com.example.sellingserviceapp.model.domain.CategoryDomain
import com.example.sellingserviceapp.model.domain.FormatsDomain
import com.example.sellingserviceapp.model.domain.NewServiceDomain
import com.example.sellingserviceapp.model.domain.PriceTypeDomain
import com.example.sellingserviceapp.model.domain.ServiceDomain
import com.example.sellingserviceapp.model.domain.SubcategoryDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditServiceViewModel @Inject constructor(
    private val dataManager: DataManager
): ViewModel() {

    var editServiceState by mutableStateOf<EditServiceState>(EditServiceState.Loading)
    var serviceDomain by mutableStateOf<ServiceDomain>(ServiceDomain.EMPTY)
    var editService by mutableStateOf<NewServiceDomain>(NewServiceDomain.EMPTY)
    var categories by mutableStateOf<List<CategoryDomain>>(emptyList())
    var formats by mutableStateOf<List<FormatsDomain>>(emptyList())
    var priceTypes by mutableStateOf<List<PriceTypeDomain>>(emptyList())

    private val _subcategories = MutableStateFlow<List<SubcategoryDomain>>(emptyList())
    val subcategories: StateFlow<List<SubcategoryDomain>> = _subcategories.asStateFlow()

    fun initEditService(service: ServiceDomain) {
        editService = editService.copy(
            tittle = service.tittle,
            description = service.description ?: "",
            duration = service.duration?.toString() ?: "",
            subcategoryName = service.subcategoryName,
            subcategoryId = _subcategories.value.find { it.code == service.subcategoryCode }?.id ?: 0,
            price = service.price?.toString() ?: "",
            priceTypeId = priceTypes.find { it.code == service.priceTypeCode }?.id ?: 0,
            formatsIds = service.formats?.mapNotNull { serviceFormat ->
                formats.find { viewModelFormat ->
                    viewModelFormat.code == serviceFormat.code
                } ?.id
            } ?: emptyList(),
            address = service.formats?.firstNotNullOfOrNull { it.address } ?: ""
        )
        editServiceState = EditServiceState.EditService
    }

    fun getSubcategories(categoryId: Int?) {
        viewModelScope.launch {
            if (categoryId != null) {
                dataManager.requestSubcategories(categoryId)
                val getSubcategoriesJob = async { dataManager.getSubcategories(categoryId) }
                _subcategories.value = getSubcategoriesJob.await()
            } else
            {
                _subcategories.value = emptyList()
            }
        }
    }
}