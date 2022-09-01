package cz.minarik.alzatest.di

import cz.minarik.alzatest.data.repository.CharacterRepositoryImpl
import cz.minarik.alzatest.data.repository.ProductRepositoryImpl
import cz.minarik.alzatest.domain.repository.CharacterRepository
import cz.minarik.alzatest.domain.repository.ProductRepository
import cz.minarik.alzatest.domain.use_case.get_categories.GetCategoriesUseCase
import cz.minarik.alzatest.domain.use_case.get_product_detail.GetProductDetailUseCase
import cz.minarik.alzatest.domain.use_case.get_products.GetProductsUseCase
import cz.minarik.alzatest.ui.screens.home.HomeScreenViewModel
import cz.minarik.alzatest.ui.screens.products.detail.ProductDetailScreenViewModel
import cz.minarik.alzatest.ui.screens.products.list.ProductListScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<CharacterRepository> {
        CharacterRepositoryImpl(
            get(),
            get(),
        )
    }

    single<ProductRepository> {
        ProductRepositoryImpl(
            get(),
        )
    }

    single {
        GetCategoriesUseCase(
            get(),
        )
    }

    single {
        GetProductsUseCase(
            get(),
        )
    }

    single {
        GetProductDetailUseCase(
            get(),
        )
    }

    viewModel {
        HomeScreenViewModel(
            get(),
        )
    }

    viewModel { (categoryId: String) ->
        ProductListScreenViewModel(
            categoryId,
            get(),
        )
    }

    viewModel { (productId: String) ->
        ProductDetailScreenViewModel(
            productId,
            get(),
        )
    }
}