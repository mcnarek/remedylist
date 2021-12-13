package co.uk.healthera.healthera.rest.interceptors

import android.content.Context
import android.net.ConnectivityManager
import co.uk.healthera.healthera.R
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.EMPTY_RESPONSE
import java.io.IOException
import javax.inject.Inject


/**
 * Created by Narek Hayrapetyan on 13 Dec 2021.
 * Copyright: Digitain
 * E-Mail: narek.hayrapetyan@digitain.com
 */
class ConnectivityInterceptor @Inject constructor(@ApplicationContext private val context: Context) :
    Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!isConnected) {
            return Response.Builder()
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .body(EMPTY_RESPONSE)
                .code(503)
                .message(context.getString(R.string.no_internet_error))
                .build()
            // Throwing our custom exception 'NoConnectivityException'
        } else {
            val builder: Request.Builder = chain.request().newBuilder()
            chain.proceed(builder.build())
        }
    }

    private val isConnected: Boolean
        get() {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = connectivityManager.activeNetworkInfo
            return netInfo != null && netInfo.isConnected
        }
}
