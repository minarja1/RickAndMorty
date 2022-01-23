package cz.minarik.alzatest.data.remote

import cz.minarik.alzatest.AlzaApplication
import cz.minarik.alzatest.BuildConfig
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException

class MockCategoriesInterceptor : Interceptor {

    private val SUCCESS_CODE = 200

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url.toUri().toString()
        if (BuildConfig.DEBUG && uri.contains("v1/floors")) {
            val responseString =
                AlzaApplication.applicationContext.assets.open("categories_mock_response.json").bufferedReader()
                    .use { it.readText() }
            return chain.proceed(chain.request())
                .newBuilder()
                .code(SUCCESS_CODE)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(responseString.toByteArray().toResponseBody("application/json".toMediaTypeOrNull()))
                .addHeader("content-type", "application/json")
                .build()
        }

        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }


}