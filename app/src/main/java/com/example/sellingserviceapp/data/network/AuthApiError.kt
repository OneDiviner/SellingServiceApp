package com.example.sellingserviceapp.data.network

sealed class AuthApiError(message: String): Throwable(message) {
    class NetworkError(message: String) : AuthApiError(message)
    class HttpError(val code: Int, message: String) : AuthApiError(message)
    class EmptyBody(message: String = "Empty body") : AuthApiError(message)
    class UnknownError(message: String) : AuthApiError(message)
}