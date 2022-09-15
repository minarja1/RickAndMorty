package cz.minarik.alzatest.common.base

import androidx.lifecycle.ViewModel
import cz.minarik.alzatest.AlzaApplication
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.cancel

open class BaseViewModel : ViewModel() {

    private val uiJob = SupervisorJob()
    private val ioJob = SupervisorJob()
    private val defaultJob = SupervisorJob()

    companion object {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, ex ->
            if (ex !is CancellationException) {//no need to log those
                //log exception
            }
            ex.printStackTrace()
        }
    }

    /**
     * Use this dispatcher to run a coroutine on the main Android thread.
     * This should be used only for interacting with the UI and performing quick work.
     * Examples include calling suspend functions, running Android UI framework operations, and updating LiveData objects.
     */
    protected val uiScope = CoroutineScope(Dispatchers.Main + uiJob + coroutineExceptionHandler)

    /**
     * This dispatcher is optimized to perform disk or network I/O outside of the main thread.
     * Examples include using the Room component, reading from or writing to files, and running any network operations.
     */
    protected val ioScope = CoroutineScope(Dispatchers.IO + ioJob + coroutineExceptionHandler)

    /**
     * This dispatcher is optimized to perform CPU-intensive work outside of the main thread.
     * Example use cases include sorting a list and parsing JSON.
     */
    protected val defaultScope =
        CoroutineScope(Dispatchers.Default + defaultJob + coroutineExceptionHandler)


    /**
     * For work that should be bound to the lifecycle of this ViewModel.
     *
     * If the lifecycle dies, the work WILL be cancelled.
     *
     * following this principle: https://developer.android.com/kotlin/coroutines/coroutines-best-practices#create-coroutines-data-layer
     *
     * example usage: network requests that only display data on response
     */
    protected fun launch(
        operation: (suspend () -> Unit)
    ) {
        ioScope.launch {
            operation()
        }
    }

    /**
     * For work that should not be cancelled with the scope of this ViewModel = is scoped to Application.
     *
     * following this principle: https://developer.android.com/kotlin/coroutines/coroutines-best-practices#create-coroutines-data-layer
     *
     * example usages: working with DB, network requests that trigger something other than UI on response
     */
    protected fun launchGlobal(
        operation: (suspend () -> Unit)
    ) {
        AlzaApplication.globalCoroutineScope.launch {
            operation()
        }
    }

    override fun onCleared() {
        super.onCleared()
        uiJob.cancel()
        ioJob.cancel()
        defaultJob.cancel()
        ioScope.cancel()
        uiScope.cancel()
        defaultScope.cancel()
    }
}
