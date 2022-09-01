package cz.minarik.alzatest

import android.app.Application
import android.content.Context
import android.content.res.Resources
import coil.Coil
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.memory.MemoryCache
import coil.util.CoilUtils
import cz.minarik.alzatest.common.base.BaseViewModel
import cz.minarik.alzatest.di.appModule
import cz.minarik.alzatest.di.dbModule
import cz.minarik.alzatest.di.networkModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class AlzaApplication : Application() {
    private val globalCoroutineScope =
        CoroutineScope(Dispatchers.IO + SupervisorJob() + BaseViewModel.coroutineExceptionHandler)

    companion object {
        lateinit var sharedInstance: AlzaApplication
            private set

        val applicationContext: Context
            get() = sharedInstance.baseContext

        val res: Resources
            get() = sharedInstance.resources

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
            modules(listOf(appModule, networkModule, dbModule))
        }
    }
}