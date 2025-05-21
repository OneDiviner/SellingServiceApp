package com.example.sellingserviceapp.ui.screen.profile.editProfile

import com.example.sellingserviceapp.model.domain.UserDomain

object EditProfileValidators {

    private const val CONTAINS_DIGITS_ERROR = "Поле не должно содержать цифры"

    fun UserDomain.validateSecondName(): String? {
        if (secondName.isBlank()) {
            return "Фамилия не может быть пустой"
        }
        if (secondName.any { it.isDigit() }) {
            return CONTAINS_DIGITS_ERROR
        }
        return null
    }
    fun UserDomain.validateFirstName(): String? {
        if (firstName.isBlank()) {
            return "Имя не может быть пустым"
        }
        if (firstName.any { it.isDigit() }) {
            return CONTAINS_DIGITS_ERROR
        }
        return null
    }
    fun UserDomain.validateLastName(): String? {
        if (lastName?.any { it.isDigit() } == true) {
            return CONTAINS_DIGITS_ERROR
        }
        return null
    }
    fun UserDomain.validatePhoneNumber(): String? {
        return null
    }
    fun UserDomain.isChanged(originalUser: UserDomain): UserDomain {
        return UserDomain(
            firstName = firstName,
            secondName = secondName,
            lastName = lastName,
            email = email,
            phoneNumber = if(originalUser.phoneNumber == phoneNumber) null else phoneNumber,
            avatar = avatar
        )
    }
}