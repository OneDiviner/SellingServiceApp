package com.example.sellingserviceapp.model

import com.example.sellingserviceapp.data.network.booking.Booking
import com.example.sellingserviceapp.data.network.feedback.model.FeedbackDto
import com.example.sellingserviceapp.model.domain.ServiceDomain
import com.example.sellingserviceapp.model.domain.UserDomain
import com.example.sellingserviceapp.model.dto.UserDto

data class FeedbackWithData(
    val feedback: FeedbackDto,
    val user: UserDomain,
    val service: ServiceDomain,
) {
    companion object {

        val EMPTY = FeedbackWithData(
            feedback = FeedbackDto.EMPTY,
            user = UserDomain.EMPTY,
            service = ServiceDomain.EMPTY
        )
    }
}