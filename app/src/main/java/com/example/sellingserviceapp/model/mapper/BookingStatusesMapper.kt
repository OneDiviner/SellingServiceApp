package com.example.sellingserviceapp.model.mapper

import com.example.sellingserviceapp.ui.screen.order.DialogState

object BookingStatusMapper {
    private val bookingDialogStateMap = mapOf(
        "STATUS_CREATED" to DialogState.NewBooking,
        "STATUS_CANCELED" to DialogState.RejectedByClientBooking,
        "STATUS_REJECTED" to DialogState.RejectedByExecutorBooking,
        "STATUS_CONFIRMED" to DialogState.ConfirmedBooking,
        "STATUS_PENDING" to DialogState.NewBooking,
        "STATUS_COMPLETED" to DialogState.NewBooking,
        "STATUS_EXPIRED" to DialogState.NewBooking
    )
    fun bookingDialogStateMap(status: String): DialogState {
        return bookingDialogStateMap[status] ?: DialogState.NewBooking
    }
}



