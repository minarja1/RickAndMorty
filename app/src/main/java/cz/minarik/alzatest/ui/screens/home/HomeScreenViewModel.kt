package cz.minarik.alzatest.ui.screens.home

import cz.minarik.alzatest.common.base.BaseViewModel
import cz.minarik.alzatest.common.base.FailedWithError
import cz.minarik.alzatest.common.base.Loading
import cz.minarik.alzatest.common.base.SuccessWithData
import cz.minarik.alzatest.domain.use_case.get_categories.GetCategoriesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeScreenViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(HomeScreenState())
    val state: Flow<HomeScreenState> = _state

    init {
        getCategories()
    }

    fun getCategories() {
        getCategoriesUseCase().onEach { result ->
            when (result) {
                is SuccessWithData -> {
                    _state.value = HomeScreenState(
                        characters = result.content,
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