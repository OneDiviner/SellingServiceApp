package com.example.sellingserviceapp.model.domain

data class ServiceDomain(
    val id: Int,
    val userId: Int,
    val tittle: String,
    val description: String?,
    val duration: Int?,
    val photoPath: String?,
    val photo: String?,
    val price: Int?,
    val createdAt: String?,
    val updatedAt: String?,
    val formats: List<FormatsDomain>?,
    val priceTypeName: String?,
    val priceTypeCode: String?,
    val statusCode: String,
    val statusName: String,
    val categoryCode: String,
    val categoryName: String,
    val subcategoryCode: String,
    val subcategoryName: String
) {
    companion object {
        val EMPTY = ServiceDomain(
            id = 0,
            userId = 0,
            tittle = "",
            description = "",
            duration = 0,
            photoPath = "",
            photo = "",
            price = 0,
            createdAt = "",
            updatedAt = "",
            formats = emptyList(),
            priceTypeName = "",
            priceTypeCode = "",
            statusCode = "",
            statusName = "",
            categoryCode = "",
            categoryName = "",
            subcategoryCode = "",
            subcategoryName = "",
        )
    }
}