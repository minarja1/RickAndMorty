package cz.minarik.alzatest.data.remote

import android.util.Log
import cz.minarik.alzatest.data.remote.exception.GeneralApiException
import cz.minarik.alzatest.data.remote.exception.NoConnectionException
import cz.minarik.alzatest.data.remote.exception.TimeoutConnectionException
import kotlinx.coroutines.CancellationException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

class ApiRequest {
    companion object {
        private val TAG = ApiRequest::class.java.simpleName
        suspend fun <T> getResult(call: suspend () -> Response<T>): T? {
            try {
                val response = call()

                return if (response.isSuccessful) {
                    response.body() ?: throw GeneralApiException()
                } else {
                    throw GeneralApiException()
                }
            } catch (e: Exception) {
                if (e is CancellationException) {
                    //coroutine was cancelled -> ignore result but don't throw exception
                    return null
                }
                Log.e(TAG, e.localizedMessage ?: "")
                //todo log error
                when (e) {
                    is IOException -> when (e) {
                        is SocketTimeoutException -> throw TimeoutConnectionException()
                        is NoConnectionException -> throw e
                        else -> throw GeneralApiException()
                    }
                    else -> throw GeneralApiException()
                }
            }
        }
    }
}