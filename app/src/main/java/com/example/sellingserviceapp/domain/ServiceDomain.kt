package com.example.sellingserviceapp.domain

data class ServiceDomain(
    val id: Int,
    val userId: Int,
    val tittle: String,
    val description: String?,
    val duration: Int?,
    val photoPath: String,
    val photo: String?,
    val price: Int?,
    val createdAt: String?,
    val updatedAt: String?,
    val formats: List<ServiceDomain>?,
    val priceType: String?,
    val status: String,
    val category: String,
    val subcategory: String
)