package com.example.popcorn.core.model

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

@Keep
//@JsonClass(generateAdapter = true)
data class ErrorHandler(
    val message: Any?,
    val code: String? = "",
    var responseCode: Int? = 0
)

@Keep
//@JsonClass(generateAdapter = true)
data class MessageMap(
    val message: Map<String, List<String>>
)

enum class ErrorCode(val value: Int) {
    NoInternet(1000),
    TimeOut(1001),
    Host(1002),
    Unknown(1003),
    JsonResponse(1004),
    JsonErrorResponse(1005),
    NullResponse(1006),
    Validation(1007),
    INTERNAL(1008),
    Auth(401)
}