package com.example.sellingserviceapp.ui.screen.authentication.registration

import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonState
import com.example.sellingserviceapp.ui.screen.authentication.state.TextFieldState

interface StateUpdater {
    fun timeLeftUpdater(value: Int)
    fun tokenUpdater(value: String)
    fun userStatusUpdater(value: String)
    fun nextButtonUpdater(nextButtonState: ButtonState)
    fun emailConfirmCodeUpdater(emailConfirmCodeState: TextFieldState)
    fun requestNewCodeButtonUpdater(requestNewCodeButtonState: ButtonState)
    fun bottomSheetStateUpdater(isOpen: Boolean)
} //TODO: Разделить на разные интерфейсы