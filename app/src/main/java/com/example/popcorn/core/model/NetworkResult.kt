package com.example.popcorn.core.model


sealed class NetworkResult<out T> {
    data class Success<out T>(val body:T): NetworkResult<T>()
    data class Error<out T>(val exception: ErrorHandler) : NetworkResult<T>()
}

