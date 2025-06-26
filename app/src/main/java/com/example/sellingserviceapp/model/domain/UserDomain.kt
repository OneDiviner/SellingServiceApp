package com.example.sellingserviceapp.model.domain

data class UserDomain(
    val id: Int,
    val firstName: String,
    val secondName: String,
    val lastName: String?,
    val email: String,
    val phoneNumber: String?,
    val avatar: String?
) {
    companion object {
        val EMPTY = UserDomain(
            id = 0,
            email = "",
            firstName = "",
            secondName = "",
            lastName = "",
            phoneNumber = "",
            avatar = ""
        )
    }
}