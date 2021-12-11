package co.uk.healthera.healthera.rest

import android.util.Log
import retrofit2.Response
import java.io.IOException

sealed class RestResponse<T> {
    data class Success<T>(val data: T) : RestResponse<T>()

    data class Error<T>(val error: String? = null, val throwable: Throwable? = null) :
        RestResponse<T>()
}

/**
 * returns true if [Response] body is not null otherwise false.
 */
private val <T> Response<T>.nonNullBody: Boolean
    get() = body() != null


fun <T> Response<T>.fold(): RestResponse<T> {
    return try {
        if (isSuccessful) {
            if (nonNullBody) {
                RestResponse.Success(body()!!)
            } else {
                RestResponse.Error(message())
            }
        } else {
            Log.i("body", body().toString())
            RestResponse.Error(error = message())
        }
    } catch (e: IOException) {
        Log.e("Network Error -> ", e.message ?: "")
        e.printStackTrace()
        RestResponse.Error(error = e.message, throwable = e)
    }
}