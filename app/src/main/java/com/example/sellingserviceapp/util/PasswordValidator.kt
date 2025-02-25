package com.example.sellingserviceapp.util

fun validatePasswords(password: String, confirmPassword: String): String {
    return when {
        password.isEmpty() -> "Пароль не может быть пустым."
        password != confirmPassword -> "Пароли не совпадают."
        //password.length < 8 -> "Пароль должен содержать не менее 8 символов."
        else -> ""
    }
}