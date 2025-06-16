package com.example.sellingserviceapp.data.manager.formatData

import com.example.sellingserviceapp.data.local.repository.FormatsRepository
import com.example.sellingserviceapp.data.network.offer.repository.OfferRepository
import com.example.sellingserviceapp.model.domain.FormatsDomain
import com.example.sellingserviceapp.model.dto.FormatsDto
import com.example.sellingserviceapp.model.mapper.formatsDtoListToEntityList
import com.example.sellingserviceapp.model.mapper.formatsEntityListToDomainList
import javax.inject.Inject

class FormatDataRepository @Inject constructor(
    private val offerRepository: OfferRepository,
    private val formatsRepository: FormatsRepository
) : IFormatDataRepository {
    override suspend fun fetchFormats() {
        val formatsDtoList = offerRepository.getFormats()
        formatsDtoList.onSuccess { formatsDto ->
            insertFormats(formatsDto)
        }
    }

    override suspend fun insertFormats(formatsDto: List<FormatsDto>) {
        formatsRepository.insertFormats(
            formatsDtoListToEntityList(dtoList = formatsDto)
        )
    }

    override suspend fun getFormats(): List<FormatsDomain> {
        fetchFormats()
        return formatsEntityListToDomainList(formatsRepository.getFormats())
    }
}