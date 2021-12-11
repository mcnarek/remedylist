package co.uk.healthera.healthera.di

import android.util.Log
import co.uk.healthera.healthera.BuildConfig
import co.uk.healthera.healthera.rest.services.RestApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    ): OkHttpClient = OkHttpClient.Builder().apply {
        readTimeout(TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        addInterceptor(appInterceptor)
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
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): RestApiService {
        return retrofit.create(RestApiService::class.java)
    }
}