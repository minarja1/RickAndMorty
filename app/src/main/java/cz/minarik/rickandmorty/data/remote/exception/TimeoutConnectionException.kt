package cz.minarik.rickandmorty.data.remote.exception

import androidx.annotation.Keep
import cz.minarik.rickandmorty.RickAndMortyApplication
import cz.minarik.rickandmorty.R

/**
 * Exception thrown when there is no internet connection.
 */
@Keep
class TimeoutConnectionException : Exception() {
    override val message: String
        get() = RickAndMortyApplication.applicationContext.getString(R.string.common_connection_timeout)
}
