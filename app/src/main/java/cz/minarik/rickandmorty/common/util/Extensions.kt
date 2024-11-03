package cz.minarik.rickandmorty.common.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import cz.minarik.rickandmorty.common.Constants
import timber.log.Timber
import java.io.UnsupportedEncodingException
import java.net.URLDecoder

/**
 * Check if internet is available.
 *
 * @return True if internet is available, false otherwise.
 */
val Context.isInternetAvailable: Boolean
    get() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
                ?: return false
        val cap = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            ?: return false
        return cap.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

/**
 * Decode string safely.
 *
 * @return Decoded string or original string if decoding failed.
 */
fun String.decodeSafely(): String {
    return try {
        URLDecoder.decode(this, Constants.UTF_8)
    } catch (e: UnsupportedEncodingException) {
        Timber.e(e)
        this
    }
}
