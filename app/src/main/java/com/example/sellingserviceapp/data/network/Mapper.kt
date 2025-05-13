package com.example.sellingserviceapp.data.network

import android.util.Log
import com.example.sellingserviceapp.data.network.offer.response.GetCategoriesResponse
import com.example.sellingserviceapp.data.network.offer.response.GetLocationTypesResponse
import com.example.sellingserviceapp.data.network.offer.response.GetPriceTypesResponse
import com.example.sellingserviceapp.data.network.offer.response.GetSubcategoriesResponse
import com.example.sellingserviceapp.ui.screen.createService.model.Category
import com.example.sellingserviceapp.ui.screen.createService.model.LocationType
import com.example.sellingserviceapp.ui.screen.createService.model.PriceType
import com.example.sellingserviceapp.ui.screen.createService.model.Subcategory


interface IMapper {
    fun <T, R> map(dtoModel: T): List<R>
    fun categoryMap(value: String): String
    fun subcategoryMap(value: String): String
    fun priceMap(value: String): String
    fun locationMap(value: String): String
    fun statusMap(value: String): String
}

class Mapper: IMapper {

   private val categoryMap = mapOf(
        "CATEGORY_REPAIR" to "Ремонт",
        "CATEGORY_MAINTENANCE" to "Обслуживание",
        "CATEGORY_EDUCATION" to "Образование"
    )

    private val subcategoryMap = mapOf(
        "SUBCATEGORY_REPAIR_CAR" to "Ремонт автомобилей",
        "SUBCATEGORY_REPAIR_HOUSEHOLD_APPLIANCES" to "Ремонт бытовой техники",
        "SUBCATEGORY_MAINTENANCE_WINDOW" to "Обслуживание окон",
        "SUBCATEGORY_MAINTENANCE_DOOR" to "Обслуживание дверей",
        "SUBCATEGORY_EDUCATION_COURSE" to "Курсы",
        "SUBCATEGORY_EDUCATION_TUTORING" to "Репетиторство"
    )

    private val priceTypeMap = mapOf(
        "PRICE_TYPE_FOR_OFFER" to "Усл.",
        "PRICE_TYPE_FOR_KILOGRAM" to "Кг",
        "PRICE_TYPE_FOR_METER" to "М",
        "PRICE_TYPE_FOR_SQUARE_METER" to "М^2",
        "PRICE_TYPE_FOR_PIECE" to "Шт.",
        "PRICE_TYPE_FOR_LITER" to "Литр",
        "PRICE_TYPE_FOR_HOUR" to "Час",
    )

    private val locationMap = mapOf(
        "LOCATION_TYPE_OFFLINE" to "Выезжаю к клиенту",
        "LOCATION_TYPE_ONLINE" to "Работаю дистанционно",
        "LOCATION_TYPE_POSITION" to "Работаю у себя"
    )

    private val statusMap = mapOf(
        "STATUS_NOT_ACTIVE" to "Скрыта",
        "STATUS_ACTIVE" to "Активна",
        "STATUS_DELETED" to "Удалена"
    )

    override fun categoryMap(value: String): String {
        return categoryMap[value]?: value
    }

    override fun subcategoryMap(value: String): String {
        return subcategoryMap[value]?: value
    }

    override fun priceMap(value: String): String {
        return priceTypeMap[value]?: value
    }

    override fun locationMap(value: String): String {
        return locationMap[value]?: value
    }

    override fun statusMap(value: String): String {
        return statusMap[value]?: value
    }

    override fun <T, R> map(dtoModel: T): List<R> {
        return when(dtoModel) {

            is GetCategoriesResponse -> {
                dtoModel.categories.map {
                    Log.d("MAPPING", dtoModel.categories.toString())
                    Category(
                        id = it.id,
                        categoryCode = it.name,
                        categoryName =  categoryMap[it.name]?: it.name
                    ) as R
                }
            }

            is GetSubcategoriesResponse -> {
                dtoModel.subcategories.map {
                    Subcategory(
                        id = it.id,
                        subcategoryCode = it.name,
                        subcategoryName =  subcategoryMap[it.name]?: it.name
                    ) as R
                }
            }

            is GetPriceTypesResponse -> {
                dtoModel.priceTypes.map {
                    PriceType(
                        id = it.id,
                        priceTypeCode = it.name,
                        priceTypeName =  priceTypeMap[it.name]?: it.name
                    ) as R
                }
            }

            is GetLocationTypesResponse -> {
                dtoModel.locationTypes.map {
                    LocationType(
                        id = it.id,
                        locationCode = it.name,
                        locationName =  locationMap[it.name]?: it.name,
                        isPhysical = it.isPhysical
                    ) as R
                }
            }

            else -> throw IllegalArgumentException("Unsupported DTOModel Type")
        }
    }
}