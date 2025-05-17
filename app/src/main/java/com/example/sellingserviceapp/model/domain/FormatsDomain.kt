package com.example.sellingserviceapp.model.domain

data class FormatsDomain(
    val id: Int,
    val name: String,
    val code: String,
    val address: String?,
    val isPhysical: Boolean
)
