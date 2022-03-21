package com.example.popcorn.core.model

import com.exchange.app.core.model.ErrorHandler


sealed class NetworkResult<out T> {
    data class Success<out T>(val body:T): NetworkResult<T>()
    data class Error<out T>(val exception: ErrorHandler) : NetworkResult<T>()
}

