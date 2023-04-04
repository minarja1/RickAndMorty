package cz.minarik.alzatest.data.remote.exception

import androidx.annotation.Keep
import cz.minarik.alzatest.AlzaApplication
import cz.minarik.alzatest.R

/**
 * Exception thrown when there is no internet connection.
 */
@Keep
class GeneralApiException : Exception() {

    companion object {
        /**
         * General message for all general api exceptions.
         */
        val generalMessage = AlzaApplication.applicationContext.getString(R.string.general_server_error)
    }

    override val message: String
        get() = generalMessage
}
