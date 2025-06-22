package com.example.sellingserviceapp.model.domain

data class FormatsDomain(
    val id: Int,
    val name: String,
    val code: String,
    val address: String?,
    val isPhysical: Boolean
) {
    companion object {
        val TEST = FormatsDomain(
            id = 1,
            name = "Выезжаю",
            code = "",
            isPhysical = false,
            address = null
        )
    }
}
