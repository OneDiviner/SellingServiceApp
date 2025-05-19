package com.example.sellingserviceapp.ui.screen.createService.newService

import com.example.sellingserviceapp.model.domain.NewServiceDomain

object NewServiceValidators {
    fun NewServiceDomain.validateTitle(): String? {
        if (tittle.isBlank()) {
            return "Название услуги не может быть пустым"
        }
        return null
    }
    fun NewServiceDomain.validatePrice(): String? {
        if (price.isBlank()) {
            return "Вы не указали цену"
        }
        return null
    }
}