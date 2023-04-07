package cz.minarik.rickandmorty.common.network

import cz.minarik.rickandmorty.BuildConfig
import cz.minarik.rickandmorty.RickAndMortyApplication
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Creates OkHttpClient with default settings.
 */
@Suppress("MagicNumber")
fun createOkHttpClient(): OkHttpClient {

    val builder = OkHttpClient.Builder()
        .connectTimeout(45, TimeUnit.SECONDS)
        .readTimeout(240, TimeUnit.SECONDS)
        .writeTimeout(90, TimeUnit.SECONDS)
        .callTimeout(if (BuildConfig.DEBUG) 1000L else 30L, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val restBuilder = chain.request().newBuilder()
                .addHeader("accept-charset", "UTF-8")
                .addHeader("accept-language", "cs-CZ")
                .addHeader("accept", "application/json")
            chain.proceed(restBuilder.build())
        }
        .addInterceptor(ConnectivityInterceptor(RickAndMortyApplication.applicationContext))
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })

    return builder.build()
}
