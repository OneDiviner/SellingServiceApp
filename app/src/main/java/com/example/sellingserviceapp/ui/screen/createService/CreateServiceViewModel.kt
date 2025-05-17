package com.example.sellingserviceapp.ui.screen.createService

import android.util.Base64
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sellingserviceapp.data.DataManager
import com.example.sellingserviceapp.data.local.UserData
import com.example.sellingserviceapp.data.local.UserDataStorage
import com.example.sellingserviceapp.data.network.offer.response.ShortServiceData
import com.example.sellingserviceapp.data.network.offer.repository.OfferRepository
import com.example.sellingserviceapp.model.domain.ServiceDomain
import com.example.sellingserviceapp.model.dto.FormatsDto
import com.example.sellingserviceapp.ui.screen.createService.model.Category
import com.example.sellingserviceapp.ui.screen.createService.model.LocationType
import com.example.sellingserviceapp.ui.screen.createService.model.PriceType
import com.example.sellingserviceapp.ui.screen.createService.model.Service
import com.example.sellingserviceapp.ui.screen.createService.model.ShortService
import com.example.sellingserviceapp.ui.screen.createService.model.Subcategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class CreateServiceViewModel @Inject constructor(
    private val offerRepository: OfferRepository,
    private val dataManager: DataManager
): ViewModel() {

    //TODO: Сделать хранилище для услуг!!!!

    val serviceListFlow: StateFlow<List<ServiceDomain>> =
        dataManager.getServices()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    var photoBase64 by mutableStateOf("")

    var isOpen by mutableStateOf(false)

    var sheetContentState by mutableStateOf<SheetContentState>(SheetContentState.Categories)

    var isRefreshing by mutableStateOf(false)

    var serviceData by mutableStateOf<ShortService>(
        ShortService(
            tittle = "",
            description = "",
            duration = "",
            address = "",
            price = "",
            priceTypeId = 0,
            subcategoryId = 0,
            locationTypeIds = emptyList()
        )
    )

    //val service by

    var categoryList by mutableStateOf<List<Category>>(emptyList())
        private set

    var subcategoryList by mutableStateOf<List<Subcategory>>(emptyList())
        private set

    var priceTypeList by mutableStateOf<List<PriceType>>(emptyList())
        private set

    var locationTypeList by mutableStateOf<List<FormatsDto>>(emptyList())
        private set

    var service by mutableStateOf<Service>(
        Service(
            id = 0,
            userId = 0,
            tittle = "",
            description = "",
            duration = "",
            photoPath = "",
            price = "",
            createdAt = "",
            updatedAt = "",
            locationTypes = emptyList(),
            priceType = "",
            status = "",
            category = "",
            subcategory = ""
        )
    )

    init {
        getCategories()
        getPriceTypes()
        getLocationTypes()
        viewModelScope.launch {
            dataManager.updateCategoriesWithSubcategories()
        }
        viewModelScope.launch {
            dataManager.insertAllServices()
        }
        viewModelScope.launch {
            dataManager.getFormats()
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            categoryList = dataManager.getCategories()
        }

    }

    fun getSubcategories(categoryId: Int) {
        viewModelScope.launch {
            subcategoryList = dataManager.getSubcategories(categoryId)
        }
    }

    fun getPriceTypes() {
        viewModelScope.launch {
            val subcategory = offerRepository.getPriceTypes()
            subcategory.onSuccess { success->
                priceTypeList = success
                Log.d("PRICETYPE_LIST", priceTypeList.toString())
            }.onFailure {
                Log.d("PRICETYPE_LIST", "FAILURE")
            }
        }
    }
    fun getLocationTypes() {
        viewModelScope.launch {
            val subcategory = offerRepository.getFormats()
            subcategory.onSuccess { success->
                locationTypeList = success
            }
        }
    }
    fun createService() {
        viewModelScope.launch {
           val result = offerRepository.createServiceRequest(serviceData)
            result.onSuccess {
                Log.d("CREATE_SERVICE", "Success")
            }.onFailure {
                Log.d("CREATE_SERVICE", "Failure")
            }
        }
    }


    fun updateService(serviceId: Int) {
        viewModelScope.launch {
            dataManager.updateService(serviceId)
        }
    }


    fun onPhotoSelected(base64: String?) {
        photoBase64 = base64?: ""
        viewModelScope.launch {
            val decodedBytes = Base64.decode(base64, Base64.DEFAULT)
            val requestBody = decodedBytes.toRequestBody("image/jpg".toMediaTypeOrNull())
            val file = MultipartBody.Part.createFormData("multipartFile", "avatar.jpg", requestBody)
            /*val result = authRepository.updateAvatar(file)
            result.onSuccess {

            }*/
        }
    }
}