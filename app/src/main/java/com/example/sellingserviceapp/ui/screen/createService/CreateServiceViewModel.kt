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
import com.example.sellingserviceapp.ui.screen.createService.model.Category
import com.example.sellingserviceapp.ui.screen.createService.model.LocationType
import com.example.sellingserviceapp.ui.screen.createService.model.PriceType
import com.example.sellingserviceapp.ui.screen.createService.model.Service
import com.example.sellingserviceapp.ui.screen.createService.model.ShortService
import com.example.sellingserviceapp.ui.screen.createService.model.Subcategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

/*data class UserData(
    var firstName: String,
    var middleName: String,
    var lastName: String?,
    var email: String, //TODO: Обязательно вынести в защищенный модуль
    var avatarBase64: String?
)*/


@HiltViewModel
class CreateServiceViewModel @Inject constructor(
    private val offerRepository: OfferRepository,
    private val userDataStorage: UserDataStorage,
    private val dataManager: DataManager
): ViewModel() {

    //TODO: Сделать хранилище для услуг!!!!

    var photoBase64 by mutableStateOf("")

    val userFlow: Flow<UserData> = userDataStorage.getUser()

    var isOpen by mutableStateOf(false)

    var sheetContentState by mutableStateOf<SheetContentState>(SheetContentState.Categories)

    var shortServiceData by mutableStateOf<List<ShortServiceData>>(emptyList())

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

    var categoryList by mutableStateOf<List<Category>>(emptyList())
        private set

    var subcategoryList by mutableStateOf<List<Subcategory>>(emptyList())
        private set

    var priceTypeList by mutableStateOf<List<PriceType>>(emptyList())
        private set

    var locationTypeList by mutableStateOf<List<LocationType>>(emptyList())
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
        searchUserServices()
        viewModelScope.launch {
            dataManager.updateCategoriesWithSubcategories()
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
            val subcategory = offerRepository.getLocationTypes()
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
    fun searchUserServices() {
        viewModelScope.launch {
            isRefreshing = true
            val result = offerRepository.searchUserServices(0, 10)
            result.onSuccess { success ->
                Log.d("CREATE_SERVICE_DATA_TITTLE", success.services.toString())
                shortServiceData = success.services
            }
            isRefreshing = false
        }
    }

    fun getService(id: Int) {
        viewModelScope.launch {
            val result = offerRepository.getService(id)
            result.onSuccess { success ->
                service = service.copy(
                    id = success.id,
                    userId = success.userId,
                    tittle = success.tittle,
                    description = success.description,
                    duration = success.duration,
                    photoPath = success.photoPath,
                    price = success.price,
                    createdAt = success.createdAt,
                    updatedAt = success.updatedAt,
                    locationTypes = success.locationTypes,
                    priceType = success.priceType,
                    status = success.status,
                    category = success.category,
                    subcategory = success.subcategory
                )
            }.onFailure { failure ->
                Log.d("GET_SERVICE_VIEW_MODEL_FAILURE", failure.message?: "")
            }
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