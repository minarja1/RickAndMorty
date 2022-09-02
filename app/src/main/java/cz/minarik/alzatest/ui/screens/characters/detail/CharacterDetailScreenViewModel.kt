package cz.minarik.alzatest.ui.screens.characters.detail

import cz.minarik.alzatest.common.base.BaseViewModel
import cz.minarik.alzatest.common.base.FailedWithError
import cz.minarik.alzatest.common.base.Loading
import cz.minarik.alzatest.common.base.SuccessWithData
import cz.minarik.alzatest.domain.use_case.get_product_detail.GetCharacterDetailUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CharacterDetailScreenViewModel(
    private val characterId: String,
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(ProductDetailScreenState())
    val state: Flow<ProductDetailScreenState> = _state

    init {
        getProductDetail()
    }

    fun getProductDetail() {
        getCharacterDetailUseCase(characterId).onEach { result ->
            when (result) {
                is SuccessWithData -> {
                    _state.value = ProductDetailScreenState(
                        character = result.content,
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