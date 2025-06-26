package com.example.sellingserviceapp.model

import com.example.sellingserviceapp.model.dto.ServiceDto

data class ServiceWithRating(
    val service: ServiceDto,
    val rating: Double
)