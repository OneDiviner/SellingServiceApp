package com.example.sellingserviceapp.model.domain

import com.example.sellingserviceapp.data.network.offer.request.CreateServiceRequest
import com.google.gson.annotations.SerializedName

data class NewServiceDomain(
    val tittle: String,
    val description: String,
    val duration: String,
    val address: String,
    val price: String,
    val priceTypeId: Int,
    val subcategoryName: String,
    val subcategoryId: Int,
    val formatsIds: List<Int>
) {
    companion object {
        val EMPTY = NewServiceDomain(
            tittle = "",
            description = "",
            duration = "",
            address = "",
            price = "",
            priceTypeId = 0,
            subcategoryName = "",
            subcategoryId = 0,
            formatsIds = emptyList(),
        )
    }
}