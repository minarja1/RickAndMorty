package cz.minarik.alzatest.ui.screens.products.list

import cz.minarik.alzatest.common.base.BaseViewModel
import cz.minarik.alzatest.common.base.FailedWithError
import cz.minarik.alzatest.common.base.Loading
import cz.minarik.alzatest.common.base.SuccessWithData
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
                    _state.value = ProductListScreenState(
                        products = result.content,
                    )
                }
                is FailedWithError -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.error,
                    )
                }
                is Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true,
                        error = ""
                    )
                }
            }
        }.launchIn(ioScope)
    }
}