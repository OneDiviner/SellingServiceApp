package com.example.sellingserviceapp.data.manager.categoryData

import com.example.sellingserviceapp.data.local.repository.categories.ICategoriesRepository
import com.example.sellingserviceapp.data.network.offer.repository.OfferRepository
import com.example.sellingserviceapp.model.domain.CategoryDomain
import com.example.sellingserviceapp.model.dto.CategoryDto
import com.example.sellingserviceapp.model.mapper.categoriesDtoListToEntityList
import com.example.sellingserviceapp.model.mapper.categoriesEntityListToDomainList
import javax.inject.Inject

class CategoryDataRepository @Inject constructor(
    private val offerRepository: OfferRepository,
    private val categoriesRepository: ICategoriesRepository
) : ICategoryDataRepository {
    override suspend fun fetchCategories() {
        val fetchCategoriesDto = offerRepository.getCategories()
        fetchCategoriesDto.onSuccess {
            insertCategories(it)
        }
    }

    override suspend fun insertCategories(categoriesDto: List<CategoryDto>) {
        categoriesRepository.insertCategories(categoriesDtoListToEntityList(categoriesDto))
    }

    override suspend fun getCategories(): List<CategoryDomain> {
        return categoriesEntityListToDomainList(categoriesRepository.getCategories())
    }
}