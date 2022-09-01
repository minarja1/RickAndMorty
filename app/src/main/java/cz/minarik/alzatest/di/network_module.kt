package cz.minarik.alzatest.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import cz.minarik.alzatest.AlzaApplication
import cz.minarik.alzatest.R
import cz.minarik.alzatest.common.network.createOkHttpClient
import cz.minarik.alzatest.common.network.createRetrofit
import cz.minarik.alzatest.data.remote.AlzaApiService
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

    single {
        ApolloClient.Builder()
            .serverUrl(AlzaApplication.applicationContext.getString(R.string.api_base_url_rick_and_morty))
            .okHttpClient(createOkHttpClient())
            .build()
    }

}