package cz.minarik.alzatest.di

import androidx.room.Room
import cz.minarik.alzatest.BuildConfig
import cz.minarik.alzatest.data.database.AlzaDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dbModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(), AlzaDatabase::class.java,
            dbName()
        )
            .build()
    }
    single { get<AlzaDatabase>().categoryDao() }
}

fun dbName(): String {
    return if (BuildConfig.DEBUG) {
        "beer-sport-db-${BuildConfig.VERSION_CODE}"
    } else {
        "beer-sport"
    }
}