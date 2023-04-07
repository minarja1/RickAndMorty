package cz.minarik.rickandmorty.di

import cz.minarik.rickandmorty.data.repository.CharacterRepositoryImpl
import cz.minarik.rickandmorty.data.repository.EpisodeRepositoryImpl
import cz.minarik.rickandmorty.domain.repository.CharacterRepository
import cz.minarik.rickandmorty.domain.repository.EpisodeRepository
import cz.minarik.rickandmorty.domain.usecase.getcharacterdetail.GetCharacterDetailUseCase
import cz.minarik.rickandmorty.domain.usecase.getepisodedetail.GetEpisodeDetailUseCase
import cz.minarik.rickandmorty.ui.screens.characters.detail.CharacterDetailScreenViewModel
import cz.minarik.rickandmorty.ui.screens.episodes.detail.EpisodeDetailScreenViewModel
import cz.minarik.rickandmorty.ui.screens.home.HomeScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * App module DI for Koin.
 */
val appModule = module {

    single<CharacterRepository> {
        CharacterRepositoryImpl(
            get(),
        )
    }

    single<EpisodeRepository> {
        EpisodeRepositoryImpl(
            get(),
        )
    }

    single {
        GetCharacterDetailUseCase(
            get(),
        )
    }

    single {
        GetEpisodeDetailUseCase(
            get(),
        )
    }

    viewModel {
        HomeScreenViewModel(
            get(),
            get(),
        )
    }

    viewModel { (characterId: String) ->
        CharacterDetailScreenViewModel(
            characterId,
            get(),
        )
    }

    viewModel { (episodeId: String) ->
        EpisodeDetailScreenViewModel(
            episodeId,
            get(),
        )
    }
}
