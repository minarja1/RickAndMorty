package cz.minarik.alzatest.data.remote.exception

import androidx.annotation.Keep
import cz.minarik.alzatest.AlzaApplication
import cz.minarik.alzatest.R
import java.io.IOException

/**
 * Exception thrown when there is no internet connection.
 */
@Keep
class NoConnectionException : IOException() {
    override val message: String
        get() = AlzaApplication.applicationContext.getString(R.string.common_no_internet_connection)
}
