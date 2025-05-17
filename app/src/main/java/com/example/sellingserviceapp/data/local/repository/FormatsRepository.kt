package com.example.sellingserviceapp.data.local.repository

import com.example.sellingserviceapp.data.local.dao.FormatsDao
import com.example.sellingserviceapp.model.entity.FormatsEntity
import javax.inject.Inject

interface IFormatsRepository {
    suspend fun insertFormats(formats: List<FormatsEntity>)
    suspend fun getFormats(): List<FormatsEntity>
}

class FormatsRepository @Inject constructor(
    private val formatsDao: FormatsDao
): IFormatsRepository {
    override suspend fun insertFormats(formats: List<FormatsEntity>) {
        formatsDao.insertFormats(formats)
    }

    override suspend fun getFormats(): List<FormatsEntity> {
        return formatsDao.getFormats()
    }
}