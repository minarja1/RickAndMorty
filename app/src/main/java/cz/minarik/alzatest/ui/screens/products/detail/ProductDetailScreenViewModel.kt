package cz.minarik.alzatest.ui.screens.products.detail

import cz.minarik.alzatest.common.base.BaseViewModel
import cz.minarik.alzatest.common.base.FailedWithError
import cz.minarik.alzatest.common.base.Loading
import cz.minarik.alzatest.common.base.SuccessWithData
import cz.minarik.alzatest.domain.use_case.get_product_detail.GetProductDetailUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProductDetailScreenViewModel(
    private val productId: String,
    private val getProductDetailUseCase: GetProductDetailUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(ProductDetailScreenState())
    val state: Flow<ProductDetailScreenState> = _state

    init {
        getProductDetail()
    }

    fun getProductDetail() {
        getProductDetailUseCase(productId.toLong()).onEach { result ->
            when (result) {
                is SuccessWithData -> {
                    _state.value = ProductDetailScreenState(
                        product = result.content,
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