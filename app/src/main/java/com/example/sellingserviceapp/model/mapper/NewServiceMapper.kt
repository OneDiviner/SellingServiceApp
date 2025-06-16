package com.example.sellingserviceapp.model.mapper

import com.example.sellingserviceapp.model.domain.NewServiceDomain
import com.example.sellingserviceapp.model.dto.NewServiceDto

object NewServiceMapper {
    fun NewServiceDomain.toDto(): NewServiceDto {
        if (tittle.isEmpty()) {
            return NewServiceDto(
                tittle = "",
                description = "",
                duration = 0,
                address = "",
                price = 0,
                priceTypeId = 0,
                subcategoryId = 0,
                locationTypeIds = emptyList()
            )
        }
        return NewServiceDto(
            tittle = tittle,
            description = description,
            duration = duration.toInt(),
            address = address,
            price = price.toInt(),
            priceTypeId = priceTypeId,
            subcategoryId = subcategoryId,
            locationTypeIds = formatsIds
        )
    }
}