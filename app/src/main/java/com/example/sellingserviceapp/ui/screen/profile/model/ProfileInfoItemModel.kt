package com.example.sellingserviceapp.ui.screen.profile.model

import androidx.compose.ui.graphics.vector.ImageVector

data class ProfileInfoItemModel(
    val icon: ImageVector, // Иконка
    val text: String, // Текст
    val description: String // Описание для доступности
)