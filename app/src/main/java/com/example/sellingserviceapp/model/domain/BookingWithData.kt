package com.example.sellingserviceapp.model.domain

import com.example.sellingserviceapp.data.network.booking.Booking
import com.example.sellingserviceapp.model.dto.UserDto

data class BookingWithData(
    val booking: Booking? = null,
    val user: UserDto? = null,
    val service: ServiceDomain? = null,
) {
    companion object {
        val EMPTY = BookingWithData(
            booking = null,
            user = null,
            service = null
        )
    }
}