package com.example.sellingserviceapp.ui.screen.createService.model

data class Address(
    val name: String,
    val location: String?
)

data class Service(
    val id: Int,
    val userId: Int,
    val tittle: String,
    val description: String,
    val duration: String,
    val photoPath: String?,
    val price: String,
    val createdAt: String,
    val updatedAt: String,
    val locationTypes: List<Address>,
    val priceType: String,
    val status: String,
    val category: String,
    val subcategory: String,
)