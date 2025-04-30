package com.example.sellingserviceapp.ui.screen.createService

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.model.offerApiRequest.CreateServiceRequest
import com.example.sellingserviceapp.data.model.offerApiResponse.Categories
import com.example.sellingserviceapp.data.model.offerApiResponse.LocationTypes
import com.example.sellingserviceapp.data.model.offerApiResponse.PriceTypes
import com.example.sellingserviceapp.data.model.offerApiResponse.Subcategories
import com.example.sellingserviceapp.data.repository.OfferRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateServiceViewModel @Inject constructor(
    private val offerRepository: OfferRepository
): ViewModel() {

    //TODO: Сделать хранилище для услуг!!!!

    var categories by mutableStateOf<List<Categories>>(emptyList())
        private set
    var subcategories by mutableStateOf<List<Subcategories>>(emptyList())
        private set
    var priceTypes by mutableStateOf<List<PriceTypes>>(emptyList())
        private set
    var locationTypes by mutableStateOf<List<LocationTypes>>(emptyList())
        private set
    var serviceData by mutableStateOf<CreateServiceRequest>(
        CreateServiceRequest(
            tittle = "",
            description = "",
            duration = 30,
            address = "",
            price = 0,
            priceTypeId = 0,
            subcategoryId = 0,
            locationTypeIds = emptyList()
        )
    )

    init {
        getCategories()
        getPriceTypes()
        getLocationTypes()
    }

    fun getCategories() {
        viewModelScope.launch {
            val category = offerRepository.getCategories()
            category.onSuccess { success ->
                categories = success.categories
            }
        }
    }

    fun getSubcategories(categoryId: Int) {
        viewModelScope.launch {
            val subcategory = offerRepository.getSubcategories(categoryId = categoryId)
            subcategory.onSuccess { success->
                subcategories = success.subcategories
            }
        }
    }

    fun getPriceTypes() {
        viewModelScope.launch {
            val subcategory = offerRepository.getPriceTypes()
            subcategory.onSuccess { success->
                priceTypes = success.priceTypes
            }
        }
    }
    fun getLocationTypes() {
        viewModelScope.launch {
            val subcategory = offerRepository.getLocationTypes()
            subcategory.onSuccess { success->
                locationTypes = success.locationTypes
            }
        }
    }
    fun createService() {
        viewModelScope.launch {
           val result = offerRepository.createServiceRequest(
                tittle = serviceData.tittle,
                description = serviceData.description,
                duration = serviceData.duration,
                address = serviceData.address,
                price = serviceData.price,
                priceTypeId = serviceData.priceTypeId,
                subcategoryId = serviceData.subcategoryId,
                locationTypeIds = serviceData.locationTypeIds
            )
            result.onSuccess {
                Log.d("CREATE_SERVICE_DATA_TITTLE", serviceData.tittle)
                Log.d("CREATE_SERVICE_DATA_DESCRIPTION", serviceData.description)
                Log.d("CREATE_SERVICE_DATA_DURATION", serviceData.duration.toString())
                Log.d("CREATE_SERVICE_DATA_ADDRESS", serviceData.address)
                Log.d("CREATE_SERVICE_DATA_PRICE", serviceData.price.toString())
                Log.d("CREATE_SERVICE_DATA_PRICE_TYPE_ID", serviceData.priceTypeId.toString())
                Log.d("CREATE_SERVICE_DATA_SUBCATEGORY_ID", serviceData.subcategoryId.toString())
                Log.d("CREATE_SERVICE_DATA_LOCATION_TYPE_IDS", serviceData.locationTypeIds.toString())
            }
        }
    }
}