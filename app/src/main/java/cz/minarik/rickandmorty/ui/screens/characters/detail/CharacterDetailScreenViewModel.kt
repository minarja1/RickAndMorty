package cz.minarik.rickandmorty.ui.screens.characters.detail

import cz.minarik.rickandmorty.common.base.BaseViewModel
import cz.minarik.rickandmorty.common.base.FailedWithError
import cz.minarik.rickandmorty.common.base.Loading
import cz.minarik.rickandmorty.common.base.SuccessWithData
import cz.minarik.rickandmorty.domain.usecase.getcharacterdetail.GetCharacterDetailUseCase
import cz.minarik.rickandmorty.ui.core.model.ButtonModel
import cz.minarik.rickandmorty.ui.core.model.ComposeViewModel
import cz.minarik.rickandmorty.ui.core.model.ErrorModel
import cz.minarik.rickandmorty.ui.core.model.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

/**
 * ViewModel for CharacterDetailScreen.
 *
 * @property characterId Id of character.
 * @property getCharacterDetailUseCase Use case for getting character detail.
 */
class CharacterDetailScreenViewModel(
    private val characterId: String,
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase
) : BaseViewModel(), ComposeViewModel<CharacterDetailScreenData, CharacterDetailScreenEvent> {

    private val _viewState = MutableStateFlow(
        UIState(
            data = CharacterDetailScreenData(),
        )
    )

    override val viewState: StateFlow<UIState<CharacterDetailScreenData>> =
        _viewState.asStateFlow()


    init {
        getCharacterDetail()
    }

    override fun onEvent(event: CharacterDetailScreenEvent) {
        when (event) {
            CharacterDetailScreenEvent.ExpandEpisodesClicked -> expandedStateChanged()
        }
    }

    /**
     * Get character detail.
     *
     * Use [viewState] to observe the result.
     */
    private fun getCharacterDetail() {
        getCharacterDetailUseCase(characterId).onEach { result ->
            when (result) {
                is SuccessWithData -> {
                    _viewState.update {
                        UIState(
                            data = CharacterDetailScreenData(
                                character = result.content,
                            ),
                        )
                    }
                }

                is FailedWithError -> {
                    _viewState.update {
                        UIState(
                            data = it.data,
                            error = ErrorModel(
                                error = result.error,
                                buttonModel = ButtonModel(
                                    text = "Retry",
                                    onClick = { getCharacterDetail() }
                                ),
                            ),
                            loading = false,
                        )
                    }
                }

                is Loading -> {
                    _viewState.update {
                        UIState(
                            data = it.data,
                            error = null,
                            loading = true,
                        )
                    }
                }
            }
        }.launchIn(ioScope)
    }

    /**
     * Expand or collapse episodes.
     */
    private fun expandedStateChanged() {
        _viewState.update {
            UIState(
                data = it.data.copy(
                    episodesExpanded = !it.data.episodesExpanded
                )
            )
        }
    }
}
