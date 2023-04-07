package cz.minarik.rickandmorty.data.remote.exception

import androidx.annotation.Keep
import cz.minarik.rickandmorty.RickAndMortyApplication
import cz.minarik.rickandmorty.R
import java.io.IOException

/**
 * Exception thrown when there is no internet connection.
 */
@Keep
class NoConnectionException : IOException() {
    override val message: String
        get() = RickAndMortyApplication.applicationContext.getString(R.string.common_no_internet_connection)
}
