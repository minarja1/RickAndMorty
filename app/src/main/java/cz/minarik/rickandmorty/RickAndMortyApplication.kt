package cz.minarik.rickandmorty

import android.app.Application
import android.content.Context
import cz.minarik.rickandmorty.common.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * Application class.
 *
 * Provides global coroutine scope, application context and resources.
 */
class RickAndMortyApplication : Application() {

    private val globalCoroutineScope =
        CoroutineScope(Dispatchers.IO + SupervisorJob() + BaseViewModel.coroutineExceptionHandler)

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

        /**
         * Global application coroutine scope.
         */
        val globalCoroutineScope: CoroutineScope
            get() = sharedInstance.globalCoroutineScope
    }

    init {
        sharedInstance = this
    }
}
