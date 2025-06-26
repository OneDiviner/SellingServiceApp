package com.example.sellingserviceapp.model.mapper

import com.example.sellingserviceapp.data.network.booking.Booking
import com.example.sellingserviceapp.data.network.booking.Status
import com.example.sellingserviceapp.model.mapper.BookingStatusAsClientMapper.codeToName
import com.example.sellingserviceapp.ui.screen.order.DialogState
import kotlin.text.get

object BookingStatusAsClientMapper {
    private val bookingStatusAsClientMap = mapOf(
        "STATUS_CREATED" to "Новые",
        "STATUS_CANCELED" to "Отмененные вами",
        "STATUS_REJECTED" to "Отклоненные исполнителем",
        "STATUS_CONFIRMED" to "Подтвежденные",
        "STATUS_PENDING" to "Ожидают выполнения",
        "STATUS_COMPLETED" to "Завершенные",
        "STATUS_EXPIRED" to "Истекли"
    )
    private val bookingStatusReasonAsClientMap = mapOf(
        "STATUS_REASON_CREATED_BY_CLIENT" to "Ожидает подтверждения исполнителем",
        "STATUS_REASON_CANCELED_BY_CLIENT" to "Отменена вами",
        "STATUS_REASON_REJECTED_BY_EXECUTOR" to "Отклонена исполнителем",
        "STATUS_REASON_CONFIRMED_BY_EXECUTOR" to "Подтверждена исполнителем",
        "STATUS_REASON_PENDING" to "Ожидает выполнения",
        "STATUS_REASON_COMPLETED" to "Завершена",
        "STATUS_REASON_EXPIRED" to "Истекла"
    )

    private val bookingAsClientDialogStateMap = mapOf(
        "STATUS_CREATED" to DialogState.NewBooking,
        "STATUS_CANCELED" to DialogState.RejectedByClientBooking,
        "STATUS_REJECTED" to DialogState.RejectedByExecutorBooking,
        "STATUS_CONFIRMED" to DialogState.ConfirmedBooking,
        "STATUS_PENDING" to DialogState.NewBooking,
        "STATUS_COMPLETED" to DialogState.NewBooking,
        "STATUS_EXPIRED" to DialogState.NewBooking
    )

    fun statusReasonAsClientMap(statusReason: String): String {
        return bookingStatusReasonAsClientMap[statusReason]?: ""
    }

    fun Status.codeToName(): Status {
        val name = bookingStatusAsClientMap[code]
        return copy(name = name)
    }

    fun Booking.codeToName(): Booking {
        //val statusName = bookingStatusAsClientMap[status]?: "NotFound"
        val statusReasonName = bookingStatusReasonAsClientMap[statusReason]?: ""
        return copy(statusReason = statusReasonName)
    }
}

fun mapStatusListAsClient(statusList: List<Status>): List<Status> {
    return statusList.map {
        it.codeToName()
    }
}

fun mapBookingListAsClient(bookingList: List<Booking>): List<Booking> {
    return bookingList.map {
        it.codeToName()
    }
}