package com.example.sellingserviceapp.ui.screen.createService.model

import com.example.sellingserviceapp.data.network.offer.request.CreateServiceRequest

data class ShortService(
    val tittle: String,
    val description: String,
    val duration: String,
    val address: String,
    val price: String,
    val priceTypeId: Int,
    val subcategoryId: Int,
    val locationTypeIds: List<Int>
)

fun ShortService.toRequest(): CreateServiceRequest {
    return CreateServiceRequest(
        tittle = tittle,
        description = description,
        duration = duration.toInt(),
        address = address,
        price = price.toInt(),
        priceTypeId = priceTypeId,
        subcategoryId = subcategoryId,
        locationTypeIds = locationTypeIds
    )
}