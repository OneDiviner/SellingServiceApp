package com.example.sellingserviceapp.util

fun validateEmail(email: String): String {
    return when {
        email.isEmpty() -> "Почтовый адресс не может быть пустым"

        " " in email -> "Почтовый адресс не дожен содержать пробелов"

        !email.contains("@") -> "Почтовый адресс должен содержать '@'"

        email.count { it == '@' } > 1 -> "Почтовый адресс не должен содержать несколько '@'"

        !email.contains(".") -> "Почтовый адресс должен содержать домен (например, .com, .ru)"

        email.matches("^[а-яА-я(){}<>,;:'/|*&^%$#!?]".toRegex()) -> "Почтовый адрес не должен содержать кириллические буквы или спецсимволы (!,#,$ и др.)"

        !email.matches("^[-a-zA-z0-9+=_~]+(?:\\.[-a-zA-z0-9+=_~]+)*@(?:[a-z0-9]([-a-zA-z0-9]{0,61}[a-z0-9])?\\.)*(amazon|abarth|abbott|aero|arpa|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel|[a-z][a-z])".toRegex()) -> "Неверный формат почтового адресса. Проверьте правильность ввода"

        else -> ""
    }
}