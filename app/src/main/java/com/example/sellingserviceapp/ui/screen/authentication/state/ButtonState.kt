package com.example.sellingserviceapp.ui.screen.authentication.state

import com.example.sellingserviceapp.data.model.response.UsersFirstStepRegisterResponse

sealed class ButtonState {
    object Loading : ButtonState()  // Состояние: загрузка
    data class Error(val error: String?): ButtonState()    // Состояние: ошибка
    data class Default(val text: String, val isClickable: Boolean) : ButtonState()  // Состояние по умолчанию с текстом
}

