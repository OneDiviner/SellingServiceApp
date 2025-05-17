package com.example.sellingserviceapp.ui.screen.createService.model

data class Category(
    val id: Int,
    val categoryCode: String,
    val categoryName: String
) {
    companion object {
        val EMPTY = Category(
            id = 0,
            categoryName = "",
            categoryCode = ""
        )
    }
}