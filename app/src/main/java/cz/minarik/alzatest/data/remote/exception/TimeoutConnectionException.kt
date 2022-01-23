package cz.minarik.alzatest.data.remote.exception

import androidx.annotation.Keep
import cz.minarik.alzatest.AlzaApplication
import cz.minarik.alzatest.R

@Keep
class TimeoutConnectionException : Exception() {
    override val message: String
        get() = AlzaApplication.applicationContext.getString(R.string.common_connection_timeout)
}
