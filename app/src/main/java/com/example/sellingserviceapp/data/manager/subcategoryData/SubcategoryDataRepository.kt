package com.example.sellingserviceapp.data.manager.subcategoryData

import com.example.sellingserviceapp.data.local.repository.ISubcategoriesRepository
import com.example.sellingserviceapp.data.network.offer.repository.OfferRepository
import com.example.sellingserviceapp.model.domain.SubcategoryDomain
import com.example.sellingserviceapp.model.dto.SubcategoryDto
import com.example.sellingserviceapp.model.mapper.subcategoriesDtoListToEntityList
import com.example.sellingserviceapp.model.mapper.subcategoriesEntityListToDomainList
import javax.inject.Inject

class SubcategoryDataRepository @Inject constructor(
    private val offerRepository: OfferRepository,
    private val subcategoriesRepository: ISubcategoriesRepository
) : ISubcategoryDataRepository {
    override suspend fun requestSubcategories(categoryId: Int) {
        val requestSubcategoriesDto = offerRepository.getSubcategories(categoryId)
        requestSubcategoriesDto.onSuccess { subcategoriesDto ->
            insertSubcategories(
                subcategoriesDto = subcategoriesDto,
                categoryId = categoryId
            )
        }
    }

    override suspend fun getSubcategories(categoryId: Int): List<SubcategoryDomain> {
        return subcategoriesEntityListToDomainList(subcategoriesRepository.getSubcategories(categoryId))
    }

    override suspend fun insertSubcategories(subcategoriesDto: List<SubcategoryDto>, categoryId: Int) {
        subcategoriesRepository.insertSubcategories(subcategoriesDtoListToEntityList(
            subcategoriesDto = subcategoriesDto,
            categoryId = categoryId
        ))
    }
}