package cz.minarik.alzatest.di

import cz.minarik.alzatest.data.repository.CharacterRepositoryImpl
import cz.minarik.alzatest.domain.repository.CharacterRepository
import cz.minarik.alzatest.domain.usecase.getcategories.GetCharactersUseCase
import cz.minarik.alzatest.domain.usecase.getproductdetail.GetCharacterDetailUseCase
import cz.minarik.alzatest.ui.screens.characters.detail.CharacterDetailScreenViewModel
import cz.minarik.alzatest.ui.screens.home.HomeScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<CharacterRepository> {
        CharacterRepositoryImpl(
            get(),
        )
    }

    single {
        GetCharactersUseCase(
            get(),
        )
    }

    single {
        GetCharacterDetailUseCase(
            get(),
        )
    }

    viewModel {
        HomeScreenViewModel(
            get(),
        )
    }

    viewModel { (productId: String) ->
        CharacterDetailScreenViewModel(
            productId,
            get(),
        )
    }
}
