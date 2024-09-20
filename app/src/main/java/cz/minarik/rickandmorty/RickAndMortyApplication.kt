package cz.minarik.rickandmorty

import android.app.Application
import android.content.Context

/**
 * Application class.
 *
 * Provides global coroutine scope, application context and resources.
 */
class RickAndMortyApplication : Application() {

    companion object {

        /**
         * Global application instance.
         */
        lateinit var sharedInstance: RickAndMortyApplication
            private set

        /**
         * Global application context.
         */
        val applicationContext: Context
            get() = sharedInstance.baseContext

    }

    init {
        sharedInstance = this
    }
}
