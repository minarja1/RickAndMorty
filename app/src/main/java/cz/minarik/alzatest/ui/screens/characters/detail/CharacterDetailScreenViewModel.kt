package cz.minarik.alzatest.ui.screens.characters.detail

import androidx.compose.runtime.mutableStateOf
import cz.minarik.alzatest.common.base.BaseViewModel
import cz.minarik.alzatest.common.base.FailedWithError
import cz.minarik.alzatest.common.base.Loading
import cz.minarik.alzatest.common.base.SuccessWithData
import cz.minarik.alzatest.domain.usecase.getcharacterdetail.GetCharacterDetailUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CharacterDetailScreenViewModel(
    private val characterId: String,
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(CharacterDetailScreenState())
    val state: Flow<CharacterDetailScreenState> = _state

    var expanded = mutableStateOf(false)

    init {
        getCharacterDetail()
    }

    fun getCharacterDetail() {
        getCharacterDetailUseCase(characterId).onEach { result ->
            when (result) {
                is SuccessWithData -> {
                    _state.value = CharacterDetailScreenState(
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

    fun expandedStateChanged() {
        expanded.value = !expanded.value
    }
}
