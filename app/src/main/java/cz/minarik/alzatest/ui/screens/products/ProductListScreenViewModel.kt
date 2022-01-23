package cz.minarik.alzatest.ui.screens.products

import cz.minarik.alzatest.common.BaseViewModel
import cz.minarik.alzatest.data.remote.FailedWithError
import cz.minarik.alzatest.data.remote.Loading
import cz.minarik.alzatest.data.remote.SuccessWithData
import cz.minarik.alzatest.domain.use_case.get_products.GetProductsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProductListScreenViewModel(
    private val categoryId: String,
    private val getCategoriesUseCase: GetProductsUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(ProductListScreenState())
    val state: Flow<ProductListScreenState> = _state

    init {
        getProducts()
    }

    fun getProducts() {
        getCategoriesUseCase(categoryId.toLong()).onEach { result ->
            when (result) {
                is SuccessWithData -> {
                    _state.value = _state.value.copy(products = result.content, isLoading = false)
                }
                is FailedWithError -> {
                    _state.value = _state.value.copy(
                        error = result.error,
                        isLoading = false,
                    )
                }
                is Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(ioScope)
    }
}