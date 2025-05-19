package com.example.sellingserviceapp.data.local.repository

import com.example.sellingserviceapp.data.local.dao.PriceTypeDao
import com.example.sellingserviceapp.model.entity.FormatsEntity
import com.example.sellingserviceapp.model.entity.PriceTypeEntity
import javax.inject.Inject

interface IPriceTypesRepository {
    suspend fun insertPriceTypes(priceTypes: List<PriceTypeEntity>)
    suspend fun getPriceTypes(): List<PriceTypeEntity>
}

class PriceTypesRepository @Inject constructor(
    private val priceTypeDao: PriceTypeDao
): IPriceTypesRepository {
    override suspend fun getPriceTypes(): List<PriceTypeEntity> {
        return priceTypeDao.getPriceTypes()
    }

    override suspend fun insertPriceTypes(priceTypes: List<PriceTypeEntity>) {
        priceTypeDao.insertPriceTypes(priceTypes)
    }
}