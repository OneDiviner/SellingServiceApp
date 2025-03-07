package com.example.sellingserviceapp.ui.screen.authentication.state

sealed class ButtonState {

    object Loading : ButtonState()  // Состояние: загрузка

    data class Error(val error: String = ""): ButtonState()    // Состояние: ошибка

    object Default : ButtonState()  // Состояние по умолчанию

    object Ready : ButtonState()  // Состояние готовности к нажатию

}

data class ButtonModel(
    val text: String,
    val state: ButtonState = ButtonState.Default
)