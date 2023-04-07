package cz.minarik.rickandmorty.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import cz.minarik.rickandmorty.RickAndMortyApplication
import cz.minarik.rickandmorty.R
import cz.minarik.rickandmorty.common.network.createOkHttpClient
import org.koin.dsl.module

/**
 * Module for network related dependencies.
 */
val networkModule = module {

    single {
        ApolloClient.Builder()
            .serverUrl(RickAndMortyApplication.applicationContext.getString(R.string.api_base_url_rick_and_morty))
            .okHttpClient(createOkHttpClient())
            .build()
    }

}
