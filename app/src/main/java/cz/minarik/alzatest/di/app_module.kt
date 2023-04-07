package cz.minarik.alzatest.di

import cz.minarik.alzatest.data.repository.CharacterRepositoryImpl
import cz.minarik.alzatest.data.repository.EpisodeRepositoryImpl
import cz.minarik.alzatest.domain.repository.CharacterRepository
import cz.minarik.alzatest.domain.repository.EpisodeRepository
import cz.minarik.alzatest.domain.usecase.getcharacterdetail.GetCharacterDetailUseCase
import cz.minarik.alzatest.domain.usecase.getepisodedetail.GetEpisodeDetailUseCase
import cz.minarik.alzatest.ui.screens.characters.detail.CharacterDetailScreenViewModel
import cz.minarik.alzatest.ui.screens.episodes.detail.EpisodeDetailScreenViewModel
import cz.minarik.alzatest.ui.screens.home.HomeScreenViewModel
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
