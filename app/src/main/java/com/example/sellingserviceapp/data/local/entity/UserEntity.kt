package com.example.sellingserviceapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "second_name") val secondName: String,
    @ColumnInfo(name = "last_name") val lastName: String?,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "phone_number") val phoneNumber: String?,
    @ColumnInfo(name = "avatar") val avatar: String?,
    @ColumnInfo(name = "avatar_path") val avatarPath: String?
) {
    companion object {
        val EMPTY = UserEntity(
            id = 0,
            email = "",
            firstName = "",
            secondName = "",
            lastName = "",
            phoneNumber = "",
            avatarPath = "",
            avatar = ""
        )
    }
}