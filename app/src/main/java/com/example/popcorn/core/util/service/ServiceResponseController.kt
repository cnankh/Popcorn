package com.example.popcorn.core.util.service

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.popcorn.R
import com.example.popcorn.core.model.NetworkResult
import com.exchange.app.core.model.*

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import retrofit2.Response
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * handles network's states
 * @param context  Context
 * @param call suspend () service
 * @return NetworkResult<T>
 */
suspend fun <T : Any> checkNetwork(
    context: Context,
    call: suspend () -> Response<T>
): NetworkResult<T> {
    val response: Response<T>
    try {
        when (isNetworkConnected(context)) {
            true -> {
                response = call.invoke()
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let {
                        Timber.tag("responseBody").d(" %s", body.toString())
                        return NetworkResult.Success(body = body)
                    }
                }
                val error = errorParser(context, response.errorBody()?.string(), response.code())
                return NetworkResult.Error(exception = error)
            }
            //no internet
            false -> {
                val errorMessage = context.getString(R.string.error_no_internet)

                val msg = Message(error = errorMessage)
                val error = ErrorHandler(message = msg, responseCode = ErrorCode.NoInternet.value)
                return NetworkResult.Error(exception = error)
            }
        }

    } catch (e: SocketTimeoutException) {
        val errorMessage = context.getString(R.string.error_timeout)

        val msg = Message(error = errorMessage)
        val error = ErrorHandler(message = msg, responseCode = ErrorCode.TimeOut.value)

        return NetworkResult.Error(exception = error)
    } catch (e: UnknownHostException) {
        var errorMessage = context.getString(R.string.error_network_host)

        val msg = Message(error = errorMessage)
        val error = ErrorHandler(message = msg, responseCode = ErrorCode.Host.value)

        return NetworkResult.Error(exception = error)
    } catch (e: JsonDataException) {
        Timber.tag("JsonDataException").d(e.toString())
        val errorMessage = context.getString(R.string.error_network_json_response)

        val msg = Message(error = errorMessage)
        val error = ErrorHandler(message = msg, responseCode = ErrorCode.JsonResponse.value)
        return NetworkResult.Error(exception = error)
    } catch (e: Exception) {
        Timber.tag("ErrorException").d(e.toString())

        Timber.tag("NetworkResult exception : ").d(e)
        val errorMessage = context.getString(R.string.error_network_unknown)
        val msg = Message(error = errorMessage)
        val error = ErrorHandler(message = msg, responseCode = ErrorCode.Unknown.value)
        return NetworkResult.Error(exception = error)
    }
}

/**
 * parse error
 * @param context  Context
 * @param errorBody: String
 * @param responseCode Int
 * @return HandelError
 */
fun errorParser(context: Context, errorBody: String?, responseCode: Int): ErrorHandler {
    return try {
        if (errorBody != null) {
            Timber.d(" %s", errorBody.toString())
            val moshi: Moshi = Moshi.Builder().build()
            val adapter: JsonAdapter<ErrorHandler> = moshi.adapter(ErrorHandler::class.java)
            var error: ErrorHandler = adapter.fromJson(errorBody)!!
            error.responseCode = responseCode
            error
        } else {
            Timber.tag("ApiNetwork").d("error upload")
            val errorMessage = context.getString(R.string.error_network_json_error_response)

            val msg = Message(error = errorMessage)
            ErrorHandler(message = msg, responseCode = ErrorCode.NullResponse.value)
        }
    } catch (e: Exception) {
        val errorMessage = context.getString(R.string.error_network_json_error_response)
        val msg = Message(error = errorMessage)
        return ErrorHandler(message = msg, responseCode = ErrorCode.JsonErrorResponse.value)
    }
}

/**
 * check internet
 * @param context: Context
 */
fun isNetworkConnected(context: Context): Boolean {
    var result = false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connectivityManager.activeNetwork ?: return false
    val activeNetwork =
        connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
    result = when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
    return result
}