package cz.minarik.alzatest.common.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import cz.minarik.alzatest.common.Constants
import java.net.URLDecoder

val Context.isInternetAvailable: Boolean
    get() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            ?: return false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val cap = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) ?: return false
            return cap.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            return connectivityManager.activeNetworkInfo?.isConnected ?: false
        }
    }

fun String.decodeSafely(): String {
    return try {
        URLDecoder.decode(this, Constants.UTF_8)
    } catch (e: Exception) {
        this
    }
}