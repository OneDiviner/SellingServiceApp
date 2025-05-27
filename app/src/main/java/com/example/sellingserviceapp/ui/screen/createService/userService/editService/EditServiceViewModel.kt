package com.example.sellingserviceapp.ui.screen.createService.userService.editService

import android.util.Log
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditServiceViewModel @Inject constructor(
    private val dataManager: DataManager
): ViewModel() {

    private val _editServiceState = MutableStateFlow<EditServiceState>(EditServiceState.Loading)
    val editServiceState: StateFlow<EditServiceState> = _editServiceState.asStateFlow()

    private val _editService = MutableStateFlow(NewServiceDomain.EMPTY)
    val editService: StateFlow<NewServiceDomain> = _editService.asStateFlow()

    private val _subcategories = MutableStateFlow<List<SubcategoryDomain>>(emptyList())
    val subcategories: StateFlow<List<SubcategoryDomain>> = _subcategories.asStateFlow()

    var serviceDomain by mutableStateOf<ServiceDomain>(ServiceDomain.EMPTY)

    var categories by mutableStateOf<List<CategoryDomain>>(emptyList())
    var formats by mutableStateOf<List<FormatsDomain>>(emptyList())
    var priceTypes by mutableStateOf<List<PriceTypeDomain>>(emptyList())

    fun initializeEditor(
        initialService: ServiceDomain,
        categoriesInitial: List<CategoryDomain>,
        formatsInitial: List<FormatsDomain>,
        priceTypesInitial: List<PriceTypeDomain>
    ) {
        viewModelScope.launch {
            _editServiceState.value = EditServiceState.Loading
            try {
                // Сохраняем переданные списки для использования
                categories = categoriesInitial
                formats = formatsInitial
                priceTypes = priceTypesInitial
                serviceDomain = initialService

                val categoryIdForSubcategories = categories.find { it.code == initialService.categoryCode }?.id
                if (categoryIdForSubcategories != null) {
                    _subcategories.value = getSubcategories(categoryIdForSubcategories)
                } else {
                    _subcategories.value = emptyList()
                }

                val determinedSubcategoryId = _subcategories.value.find { it.code == initialService.subcategoryCode }?.id ?: 0
                val determinedPriceTypeId = priceTypes.find { it.code == initialService.priceTypeCode }?.id ?: 0

                val mappedFormatsIds = initialService.formats?.mapNotNull { serviceFormat ->
                    formats.find { viewModelFormat ->
                        viewModelFormat.code == serviceFormat.code
                    }?.id
                } ?: emptyList()

                val initialAddress = initialService.formats?.firstNotNullOfOrNull { it.address } ?: ""

                _editService.value = NewServiceDomain(
                    tittle = initialService.tittle,
                    description = initialService.description ?: "",
                    duration = initialService.duration?.toString() ?: "",
                    subcategoryName = initialService.subcategoryName, // Может быть лучше найти по ID в _subcategories.value
                    subcategoryId = determinedSubcategoryId,
                    price = initialService.price?.toString() ?: "",
                    priceTypeId = determinedPriceTypeId,
                    formatsIds = mappedFormatsIds,
                    address = initialAddress
                )

                _editServiceState.value = EditServiceState.EditService
            } catch (e: Exception) {
                Log.e("EditServiceVM", "Error initializing editor", e)
                _editServiceState.value = EditServiceState.Error
            }
        }
    }

    fun editTitle(value: String) {
        _editService.value = _editService.value.copy(tittle = value)
    }

    fun selectCategory(categoryId: Int) {
        viewModelScope.launch {
            _subcategories.value = getSubcategories(categoryId)
        }
    }

    fun selectSubcategory(subcategory: SubcategoryDomain) {
        _editService.value = _editService.value.copy(
            subcategoryName = subcategory.name,
            subcategoryId = subcategory.id
        )
    }

    fun editPrice(value: String) {
        _editService.value = _editService.value.copy(price = value)
    }

    fun selectPriceType(priceType: PriceTypeDomain) {
        _editService.value = _editService.value.copy(
            priceTypeId = priceType.id
        )
    }

    fun selectDuration(value: String) {
        _editService.value = _editService.value.copy(duration = value)
    }

    fun editDescription(value: String) {
        _editService.value = _editService.value.copy(description = value)
    }

    fun selectFormats(formats: List<Int>) {
        _editService.value = _editService.value.copy(formatsIds = formats)
    }

    fun editAddress(value: String) {
        _editService.value = _editService.value.copy(address = value)
    }

    private suspend fun getSubcategories(categoryId: Int): List<SubcategoryDomain> {
        dataManager.requestSubcategories(categoryId)
        return dataManager.getSubcategories(categoryId)
    }

    fun updateService() {
        _editServiceState.value = EditServiceState.Loading
        viewModelScope.launch {
            dataManager.updateService(serviceDomain.id, _editService.value)
            _editServiceState.value = EditServiceState.EditService
        }
    }

}