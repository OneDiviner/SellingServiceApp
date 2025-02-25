package com.example.sellingserviceapp.ui.screen.authentication

sealed interface AuthenticationState {
    data object Idle: AuthenticationState // Начальное состояние
    data object Login: AuthenticationState // Вход
    data object Registration: AuthenticationState // Регистрация
    data object UserInfo: AuthenticationState // Ввод информации о пользователе
}