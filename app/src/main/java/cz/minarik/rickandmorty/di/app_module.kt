package cz.minarik.rickandmorty.di

import cz.minarik.rickandmorty.data.repository.RemoteCharacterRepository
import cz.minarik.rickandmorty.data.repository.RemoteEpisodeRepository
import cz.minarik.rickandmorty.domain.repository.CharacterRepository
import cz.minarik.rickandmorty.domain.repository.EpisodeRepository
import cz.minarik.rickandmorty.domain.usecase.getcharacterdetail.GetCharacterDetailUseCase
import cz.minarik.rickandmorty.domain.usecase.getepisodedetail.GetEpisodeDetailUseCase
import cz.minarik.rickandmorty.ui.screens.characters.detail.CharacterDetailScreenViewModel
import cz.minarik.rickandmorty.ui.screens.episodes.detail.EpisodeDetailScreenViewModel
import cz.minarik.rickandmorty.ui.screens.home.HomeScreenViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * App module DI for Koin.
 */
val appModule = module {

    single<CharacterRepository> {
        RemoteCharacterRepository(
            get(),
        )
    }

    single<EpisodeRepository> {
        RemoteEpisodeRepository(
            get(),
        )
    }

    factoryOf(::GetCharacterDetailUseCase)

    factoryOf(::GetEpisodeDetailUseCase)

    viewModelOf(::HomeScreenViewModel)

    viewModelOf(::CharacterDetailScreenViewModel)

    viewModelOf(::EpisodeDetailScreenViewModel)

}
