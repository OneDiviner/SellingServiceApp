package com.example.sellingserviceapp.data.manager.priceTypeData

import com.example.sellingserviceapp.data.local.repository.IPriceTypesRepository
import com.example.sellingserviceapp.data.network.offer.repository.OfferRepository
import com.example.sellingserviceapp.model.domain.PriceTypeDomain
import com.example.sellingserviceapp.model.dto.PriceTypeDto
import com.example.sellingserviceapp.model.mapper.priceTypesDtoListToEntityList
import com.example.sellingserviceapp.model.mapper.priceTypesEntityListToDomainList
import javax.inject.Inject

class PriceTypeDataRepository @Inject constructor(
    private val offerRepository: OfferRepository,
    private val priceTypesRepository: IPriceTypesRepository
) : IPriceTypeDataRepository {
    override suspend fun insertPriceTypes(priceTypesDto: List<PriceTypeDto>) {
        priceTypesRepository.insertPriceTypes(priceTypesDtoListToEntityList(priceTypesDto))
    }

    override suspend fun getPriceTypes(): List<PriceTypeDomain> {
        //fetchPriceTypes()
        return priceTypesEntityListToDomainList(priceTypesRepository.getPriceTypes())
    }

    override suspend fun fetchPriceTypes() {
        val priceTypesRequest = offerRepository.getPriceTypes()
        priceTypesRequest.onSuccess { priceTypesDto ->
            insertPriceTypes(priceTypesDto)
        }
    }
}