package co.uk.healthera.healthera.di

import android.content.Context
import android.util.Log
import co.uk.healthera.healthera.BuildConfig
import co.uk.healthera.healthera.rest.interceptors.ConnectivityInterceptor
import co.uk.healthera.healthera.rest.services.RestApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


/**
 * Created by Narek Hayrapetyan on 11 Dec 2021.
 * Copyright: Healthera
 * E-Mail: mcnarek@gmail.com
 */
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    private const val TIMEOUT: Long = 60L

    private const val BASE_URL: String =
        "https://34574e81-855b-4c10-8987-935950fdd23c.mock.pstmn.io/"

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @Named("appInterceptor") appInterceptor: Interceptor,
        @Named("errorInterceptor") errorInterceptor: Interceptor,
        connectivityInterceptor: ConnectivityInterceptor,
    ): OkHttpClient = OkHttpClient.Builder().apply {
        readTimeout(TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        addInterceptor(appInterceptor)
        addInterceptor(errorInterceptor)
        addInterceptor(connectivityInterceptor)
        if (BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor { Log.d("api", it) }
                .setLevel(HttpLoggingInterceptor.Level.BODY))
        }
    }.build()


    @Provides
    @Singleton
    @Named("appInterceptor")
    fun provideAppInterceptor(): Interceptor =
        Interceptor { chain: Interceptor.Chain ->
            val request = chain.request()

            val requestBuilder = request.newBuilder().apply {
                addHeader("Accept", "application/json")
                addHeader("Accept-Language", "en")
                addHeader("Content-Type", "application/json")
            }

            chain.proceed(requestBuilder.build())
        }

    @Provides
    @Singleton
    @Named("errorInterceptor")
    fun provideErrorInterceptor(): Interceptor = Interceptor(
        @Throws(IOException::class)
        fun(chain: Interceptor.Chain): Response {
            val request: Request = chain.request()
            val response: Response = chain.proceed(request)

            return try {

                if (response.code in 400..499) {
                    Log.e("Network Error:", response.message + " | code" + response.code)
                }
                if (response.code in 500..599) {
                    Log.e("Server Error:", response.message + " | code" + response.code)
                }
                response
            } catch (e: IOException) {
                response
            }
        })

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideConnectivityInterceptor(@ApplicationContext context: Context): ConnectivityInterceptor =
        ConnectivityInterceptor(context = context)

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): RestApiService {
        return retrofit.create(RestApiService::class.java)
    }
}