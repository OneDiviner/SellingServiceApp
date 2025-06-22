package com.example.sellingserviceapp.model

import com.example.sellingserviceapp.data.network.booking.Booking
import com.example.sellingserviceapp.data.network.feedback.model.FeedbackDto
import com.example.sellingserviceapp.model.domain.ServiceDomain
import com.example.sellingserviceapp.model.domain.UserDomain
import com.example.sellingserviceapp.model.dto.UserDto

data class FeedbackWithData(
    val feedback: FeedbackDto,
    val user: UserDto,
    val service: ServiceDomain,
) {
    companion object {
        val TEST1 = FeedbackWithData(
            feedback = FeedbackDto.TEST_POSITIVE,
            user = UserDto(
                id = 789, email = "reviewer1@example.com", firstName = "Иван", secondName = "Петров",
                lastName = "Сергеевич", phoneNumber = "+79001234567", avatarPath = "http://example.com/avatars/ivan.jpg",
                createdAt = "2022-01-15T10:00:00Z", updatedAt = "2023-01-15T10:00:00Z",
                role = "USER", status = "ACTIVE"
            ),
            service = ServiceDomain.PLUMBING_FIXED_ACTIVE
        )
        val TEST2 = FeedbackWithData(
            feedback = FeedbackDto.TEST_POSITIVE,
            user = UserDto(
                id = 789, email = "reviewer1@example.com", firstName = "Иван", secondName = "Петров",
                lastName = "Сергеевич", phoneNumber = "+79001234567", avatarPath = "http://example.com/avatars/ivan.jpg",
                createdAt = "2022-01-15T10:00:00Z", updatedAt = "2023-01-15T10:00:00Z",
                role = "USER", status = "ACTIVE"
            ),
            service = ServiceDomain.MASSAGE_THERAPY_ACTIVE
        )
        val TEST3 = FeedbackWithData(
            feedback = FeedbackDto.TEST_POSITIVE,
            user = UserDto(
                id = 789, email = "reviewer1@example.com", firstName = "Иван", secondName = "Петров",
                lastName = "Сергеевич", phoneNumber = "+79001234567", avatarPath = "http://example.com/avatars/ivan.jpg",
                createdAt = "2022-01-15T10:00:00Z", updatedAt = "2023-01-15T10:00:00Z",
                role = "USER", status = "ACTIVE"
            ),
            service = ServiceDomain.WEB_DEV_PROJECT_ACTIVE
        )
        val TEST4 = FeedbackWithData(
            feedback = FeedbackDto.TEST_POSITIVE,
            user = UserDto(
                id = 789, email = "reviewer1@example.com", firstName = "Иван", secondName = "Петров",
                lastName = "Сергеевич", phoneNumber = "+79001234567", avatarPath = "http://example.com/avatars/ivan.jpg",
                createdAt = "2022-01-15T10:00:00Z", updatedAt = "2023-01-15T10:00:00Z",
                role = "USER", status = "ACTIVE"
            ),
            service = ServiceDomain.PHOTO_SESSION_INACTIVE
        )
    }
}