package cz.minarik.alzatest.common.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import cz.minarik.alzatest.AlzaApplication
import cz.minarik.alzatest.common.Constants
import kotlinx.coroutines.flow.Flow

object DataStoreManager {

    private val context: Context by lazy {
        AlzaApplication.applicationContext
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(Constants.dataStoreName)

    private const val CATEGORIES_LAST_FETCH_TIME = "CATEGORIES_LAST_FETCH_TIME"

    fun getCategoriesLastFetchTime(): Flow<Long> {
        return context.dataStore.getLongData(CATEGORIES_LAST_FETCH_TIME)
    }

    suspend fun setCategoriesLastFetchTime(data: Long) {
        context.dataStore.setLongData(CATEGORIES_LAST_FETCH_TIME, data)
    }

}

