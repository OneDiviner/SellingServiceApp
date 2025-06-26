package com.example.sellingserviceapp.model.mapper

import android.util.Log
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.sellingserviceapp.R
import com.example.sellingserviceapp.ui.screen.order.DialogState

object BookingStatusMapper {
    private val bookingDialogStateMap = mapOf(
        "STATUS_CREATED" to DialogState.NewBooking,
        "STATUS_CANCELED" to DialogState.RejectedByClientBooking,
        "STATUS_REJECTED" to DialogState.RejectedByExecutorBooking,
        "STATUS_CONFIRMED" to DialogState.ConfirmedBooking,
        "STATUS_PENDING" to DialogState.Pending,
        "STATUS_COMPLETED" to DialogState.Completed,
        "STATUS_EXPIRED" to DialogState.Expired
    )
    private val bookingStatusToIconMap = mapOf(
        "STATUS_CREATED" to R.drawable.bookmark_add,
        "STATUS_CANCELED" to R.drawable.rejected_book,
        "STATUS_REJECTED" to R.drawable.rejected_book,
        "STATUS_CONFIRMED" to R.drawable.awating_book,
        "STATUS_PENDING" to R.drawable.book,
        "STATUS_COMPLETED" to R.drawable.bookmark_added,
        "STATUS_EXPIRED" to R.drawable.rejected_book
    )
    fun bookingDialogStateMap(status: String): DialogState {
        Log.d("BOOKING_DIALOG_STATE_MAP", status)
        return bookingDialogStateMap[status] ?: DialogState.NewBooking
    }

    fun bookingStatusToIconMap(status: String): Int {
        return bookingStatusToIconMap[status] ?: R.drawable.book
    }
}



