package com.example.sellingserviceapp.model.mapper

import com.example.sellingserviceapp.model.domain.NewServiceDomain
import com.example.sellingserviceapp.model.dto.NewServiceDto

object NewServiceMapper {
    fun NewServiceDomain.toDto(): NewServiceDto {
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