package com.example.sellingserviceapp.data.network

import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import dagger.Provides
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

data class BaseResponse<T>(
    @SerializedName("response")
    val response: ResponseData,

    @SerializedName("data")
    val data: T? = null
)

data class ResponseData(
    @SerializedName("message")
    val message: String?,

    @SerializedName("code")
    val code: Int?
)

data class ServiceRequestResponse(
    @SerializedName("serviceId")
    val serviceId: Int
)

sealed class AuthApiError(message: String): Throwable(message) {
    class NetworkError(message: String) : AuthApiError(message)
    class HttpError(val code: Int, message: String) : AuthApiError(message)
    class EmptyBody(message: String = "Empty body") : AuthApiError(message)
    class UnknownError(message: String) : AuthApiError(message)
}

@Singleton
class ErrorHandler @Inject constructor() {
    private var _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()


    fun setError(error: String) {
        _error.value = error
        Log.d("ERROR_HANDLER", "NEW_ERROR")
    }

    fun observeError(): StateFlow<String?> {
        return _error
    }
}