package cz.minarik.alzatest.data.remote.exception

import androidx.annotation.Keep

@Keep
class ApiException(val text: String?) : Exception() {
    override val message: String?
        get() = text
}