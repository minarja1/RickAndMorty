package cz.minarik.alzatest.di

import cz.minarik.alzatest.AlzaApplication
import cz.minarik.alzatest.R
import cz.minarik.alzatest.common.network.createOkHttpClient
import cz.minarik.alzatest.common.network.createRetrofit
import cz.minarik.alzatest.data.remote.AlzaApiService
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.dsl.module


val networkModule = module {

    single {
        AlzaApiService(
            createRetrofit(
                createOkHttpClient(),
                AlzaApplication.applicationContext.getString(R.string.api_base_url)
            )
        )
    }

}