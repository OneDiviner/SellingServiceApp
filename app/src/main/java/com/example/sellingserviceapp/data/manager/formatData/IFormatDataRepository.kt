package com.example.sellingserviceapp.data.manager.formatData

import com.example.sellingserviceapp.model.domain.FormatsDomain
import com.example.sellingserviceapp.model.dto.FormatsDto

interface IFormatDataRepository {
    suspend fun fetchFormats()
    suspend fun insertFormats(formatsDto: List<FormatsDto>)
    //suspend fun getFormatsListEntity()
    suspend fun getFormats(): List<FormatsDomain>
}