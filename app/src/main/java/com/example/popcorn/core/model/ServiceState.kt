package com.example.popcorn.core.model

sealed  class ServiceState<out T>() {

    class Loading<T>: ServiceState<T>()
    data class Success<T>(val data: T): ServiceState<T>()
    data class Error<T>(val exception: ErrorHandler): ServiceState<T>()

    companion object {

        fun <T> success(data: T): ServiceState<T> {
            return Success(data)
        }

        fun <T> error(exception: ErrorHandler): ServiceState<T> {
            return Error(exception)
        }

        fun <T> loading(): ServiceState<T> {
            return Loading()
        }

    }

}