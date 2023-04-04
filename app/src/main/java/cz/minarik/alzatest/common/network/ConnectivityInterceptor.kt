package cz.minarik.alzatest.common.network

import android.content.Context
import cz.minarik.alzatest.common.util.isInternetAvailable
import cz.minarik.alzatest.data.remote.exception.NoConnectionException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Interceptor for checking internet connection.
 */
class ConnectivityInterceptor(private val mContext: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!mContext.isInternetAvailable) {
            throw NoConnectionException()
        }

        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

}
