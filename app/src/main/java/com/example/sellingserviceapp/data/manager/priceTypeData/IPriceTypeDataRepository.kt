package com.example.sellingserviceapp.data.manager.priceTypeData

import com.example.sellingserviceapp.model.domain.PriceTypeDomain
import com.example.sellingserviceapp.model.dto.PriceTypeDto

interface IPriceTypeDataRepository {
    suspend fun fetchPriceTypes()
    suspend fun insertPriceTypes(priceTypesDto: List<PriceTypeDto>)
    suspend fun getPriceTypes(): List<PriceTypeDomain>
}