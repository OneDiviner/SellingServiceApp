package com.example.sellingserviceapp.model.domain

data class CategoryDomain(
    val id: Int,
    val name: String,
    val code: String
) {
    companion object {
        val EMPTY = CategoryDomain(
            id = 0,
            name = "",
            code = ""
        )
    }
}