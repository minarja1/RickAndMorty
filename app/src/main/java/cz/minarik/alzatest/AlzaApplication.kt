package cz.minarik.alzatest

import android.app.Application
import android.content.Context
import cz.minarik.alzatest.common.base.BaseViewModel
import cz.minarik.alzatest.di.appModule
import cz.minarik.alzatest.di.networkModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

/**
 * Application class.
 *
 * Provides global coroutine scope, application context and resources.
 */
class AlzaApplication : Application() {
    private val globalCoroutineScope =
        CoroutineScope(Dispatchers.IO + SupervisorJob() + BaseViewModel.coroutineExceptionHandler)

    companion object {

        /**
         * Global application instance.
         */
        lateinit var sharedInstance: AlzaApplication
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

    override fun onCreate() {
        super.onCreate()
        startKoin {
            //remove after update to koin 3.2.0
            //see: https://github.com/InsertKoinIO/koin/issues/1188
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@AlzaApplication)
            modules(listOf(appModule, networkModule))
        }
    }
}
