package cz.minarik.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import cz.minarik.rickandmorty.di.appModule
import cz.minarik.rickandmorty.di.networkModule
import cz.minarik.rickandmorty.navigation.Navigation
import cz.minarik.rickandmorty.navigation.NavigationV3
import org.koin.android.ext.koin.androidLogger
import org.koin.compose.KoinApplication
import org.koin.core.logger.Level
import org.koin.mp.KoinPlatformTools

/**
 * Main activity of the app.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StartKoin {
                Navigation()
            }
        }
    }
}

/**
 * Start Koin if not already started.
 */
@Composable
fun StartKoin(
    content: @Composable () -> Unit,
) {
    if (KoinPlatformTools.defaultContext().getOrNull() == null) {
        KoinApplication(application = {
            modules(appModule, networkModule)
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
        }) {
            content()
        }
    } else {
        content()
    }
}
