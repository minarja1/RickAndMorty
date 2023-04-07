package cz.minarik.rickandmorty.data.remote.exception

import androidx.annotation.Keep

/**
 * Exception thrown when there is no internet connection.
 */
@Keep
class GeneralApiException : Exception() {

    companion object {
        // fixme - this should be in strings.xml
        /**
         * General message for all general api exceptions.
         */
        const val generalMessage = "Unable to connect to server, please try again later…"
    }

    override val message: String
        get() = generalMessage
}
