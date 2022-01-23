package cz.minarik.alzatest.di

import cz.minarik.alzatest.AlzaApplication
import cz.minarik.alzatest.R
import cz.minarik.alzatest.common.createOkHttpClient
import cz.minarik.alzatest.common.createRetrofit
import cz.minarik.alzatest.data.remote.AlzaApiService
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.dsl.module


@ExperimentalSerializationApi
val networkModule = module {

    // single instance of HelloRepository
    single {
        AlzaApiService(
            createRetrofit(
                createOkHttpClient(),
                AlzaApplication.applicationContext.getString(R.string.api_base_url)
            )
        )
    }

}