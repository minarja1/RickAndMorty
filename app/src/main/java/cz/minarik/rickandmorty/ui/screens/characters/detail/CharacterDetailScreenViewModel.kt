package cz.minarik.rickandmorty.ui.screens.characters.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cz.minarik.rickandmorty.common.base.BaseViewModel
import cz.minarik.rickandmorty.common.base.FailedWithError
import cz.minarik.rickandmorty.common.base.Loading
import cz.minarik.rickandmorty.common.base.SuccessWithData
import cz.minarik.rickandmorty.domain.usecase.getcharacterdetail.GetCharacterDetailUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * ViewModel for CharacterDetailScreen.
 *
 * @property characterId Id of character.
 * @property getCharacterDetailUseCase Use case for getting character detail.
 */
class CharacterDetailScreenViewModel(
    private val characterId: String,
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(CharacterDetailScreenState())

    /**
     * State of CharacterDetailScreen.
     */
    val state: Flow<CharacterDetailScreenState> = _state

    private val _episodesExpanded = mutableStateOf(false)

    /**
     * State of episodes expansion.
     */
    val episodesExpanded: State<Boolean> = _episodesExpanded

    init {
        getCharacterDetail()
    }

    /**
     * Get character detail.
     *
     * Use [state] to observe the result.
     */
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

    /**
     * Expand or collapse episodes.
     */
    fun expandedStateChanged() {
        _episodesExpanded.value = !_episodesExpanded.value
    }
}
