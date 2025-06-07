package com.example.sellingserviceapp.model.mapper

import com.example.sellingserviceapp.data.network.booking.Status
import com.example.sellingserviceapp.model.domain.CategoryDomain
import com.example.sellingserviceapp.model.dto.CategoryDto
import com.example.sellingserviceapp.model.entity.CategoryEntity
import com.example.sellingserviceapp.model.mapper.BookingStatusMapper.codeToName
import com.example.sellingserviceapp.ui.screen.order.DialogState

object BookingStatusMapper {

    private val bookingStatusFilterMap = mapOf(
        "STATUS_CREATED" to "Новые",
        "STATUS_CANCELED" to "Отмененные",
        "STATUS_REJECTED" to "Отклоненные",
        "STATUS_CONFIRMED" to "Подтвежденные",
        "STATUS_PENDING" to "Ожидают выполнения",
        "STATUS_COMPLETED" to "Завершенные",
        "STATUS_EXPIRED" to "Истекли"
    )


    private val bookingStatusReasonByExecutorMap = mapOf(
        "STATUS_REASON_CREATED_BY_CLIENT" to "Ожидает подтверждения",
        "STATUS_REASON_CANCELED_BY_CLIENT" to "Отменена клиентом",
        "STATUS_REASON_REJECTED_BY_EXECUTOR" to "Отклонена вами",
        "STATUS_REASON_CONFIRMED_BY_EXECUTOR" to "Подтверждена",
        "STATUS_REASON_PENDING" to "Ожидает выполнения",
        "STATUS_REASON_COMPLETED" to "Завершена",
        "STATUS_REASON_EXPIRED" to "Истекла"
    )

    private val bookingStatusReasonByClientMap = mapOf(
        "STATUS_REASON_CREATED_BY_CLIENT" to "Ожидает подтверждения исполнителем",
        "STATUS_REASON_CANCELED_BY_CLIENT" to "Отменена вами",
        "STATUS_REASON_REJECTED_BY_EXECUTOR" to "Отклонена исполнителем",
        "STATUS_REASON_CONFIRMED_BY_EXECUTOR" to "Подтверждена исполнителем",
        "STATUS_REASON_PENDING" to "Ожидает выполнения",
        "STATUS_REASON_COMPLETED" to "Завершена",
        "STATUS_REASON_EXPIRED" to "Истекла"
    )

    private val bookingDialogStateMap = mapOf(
        "STATUS_CREATED" to DialogState.NewBooking,
        "STATUS_CANCELED" to DialogState.RejectedByClientBooking,
        "STATUS_REJECTED" to DialogState.RejectedByExecutorBooking,
        "STATUS_CONFIRMED" to DialogState.ConfirmedBooking,
        "STATUS_PENDING" to DialogState.NewBooking,
        "STATUS_COMPLETED" to DialogState.NewBooking,
        "STATUS_EXPIRED" to DialogState.NewBooking
    )

    fun statusReasonByExecutorMap(statusReason: String): String {
        return bookingStatusReasonByExecutorMap[statusReason]?: ""
    }

    fun statusReasonByClientMap(statusReason: String): String {
        return bookingStatusReasonByClientMap[statusReason]?: ""
    }

    fun bookingDialogStateMap(status: String): DialogState {
        return bookingDialogStateMap[status] ?: DialogState.NewBooking
    }

    fun Status.codeToName(): Status {
        val name = bookingStatusFilterMap[code]
        return copy(name = name)
    }
}

fun mapStatusListByClient(statusList: List<Status>): List<Status> {
    return statusList.map {
        it.codeToName()
    }
}

fun mapStatusListByExecutor(statusList: List<Status>): List<Status> {
    return statusList.map {
        it.codeToName()
    }
}