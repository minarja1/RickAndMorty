package cz.minarik.alzatest.common

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import cz.minarik.alzatest.AlzaApplication
import cz.minarik.alzatest.BuildConfig
import cz.minarik.alzatest.data.remote.MockCategoriesInterceptor
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


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
                .addHeader("user-agent", "okhttp/4.7.0;Google/Android SDK built for x86;10;en_GB;293;9.8.0;0;cz.alza.eshop")
                .addHeader("accept", "application/json")
            chain.proceed(restBuilder.build())
        }
        .addInterceptor(ConnectivityInterceptor(AlzaApplication.applicationContext))
//        .addInterceptor(MockCategoriesInterceptor())
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })

    return builder.build()
}

@ExperimentalSerializationApi
fun createRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
    val contentType = "application/json".toMediaType()
    val json = Json {
        ignoreUnknownKeys = true
    }

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()
}
