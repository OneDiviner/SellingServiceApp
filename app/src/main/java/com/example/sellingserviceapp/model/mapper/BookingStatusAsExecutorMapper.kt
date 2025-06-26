package com.example.sellingserviceapp.model.mapper

import com.example.sellingserviceapp.data.network.booking.Booking
import com.example.sellingserviceapp.data.network.booking.Status
import com.example.sellingserviceapp.model.mapper.BookingStatusAsExecutorMapper.codeToName

object BookingStatusAsExecutorMapper {
    private val bookingStatusAsExecutor = mapOf(
        "STATUS_CREATED" to "Новые",
        "STATUS_CANCELED" to "Отмененные клиентом",
        "STATUS_REJECTED" to "Отклоненные вами",
        "STATUS_CONFIRMED" to "Подтвежденные",
        "STATUS_PENDING" to "Ожидают выполнения",
        "STATUS_COMPLETED" to "Завершенные",
        "STATUS_EXPIRED" to "Истекли"
    )
    private val bookingStatusReasonAsExecutorMap = mapOf(
        "STATUS_REASON_CREATED_BY_CLIENT" to "Ожидает подтверждения",
        "STATUS_REASON_CANCELED_BY_CLIENT" to "Отменена клиентом",
        "STATUS_REASON_REJECTED_BY_EXECUTOR" to "Отклонена вами",
        "STATUS_REASON_CONFIRMED_BY_EXECUTOR" to "Подтверждена",
        "STATUS_REASON_PENDING" to "Ожидает выполнения",
        "STATUS_REASON_COMPLETED" to "Завершена",
        "STATUS_REASON_EXPIRED" to "Истекла"
    )

    fun statusReasonAsExecutorMap(statusReason: String): String {
        return bookingStatusReasonAsExecutorMap[statusReason]?: ""
    }

    fun Status.codeToName(): Status {
        val name = bookingStatusAsExecutor[code]
        return copy(name = name)
    }

    fun Booking.codeToName(): Booking {
        //val statusName = bookingStatusAsExecutor[status]?: ""
        val statusReasonName = bookingStatusReasonAsExecutorMap[statusReason]?: ""
        return copy(statusReason = statusReasonName)
    }
}

fun mapStatusListAsExecutor(statusList: List<Status>): List<Status> {
    return statusList.map {
        it.codeToName()
    }
}

fun mapBookingListAsExecutor(bookingList: List<Booking>): List<Booking> {
    return bookingList.map {
        it.codeToName()
    }
}