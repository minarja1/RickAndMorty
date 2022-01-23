package cz.minarik.alzatest.data.remote.exception

import androidx.annotation.Keep
import cz.minarik.alzatest.AlzaApplication
import cz.minarik.alzatest.R

@Keep
class GeneralApiException : Exception() {

    companion object {
        //todo
        val generalMessage = AlzaApplication.applicationContext.getString(R.string.general_server_error)
    }

    override val message: String
        get() = generalMessage
}