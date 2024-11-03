package cz.minarik.rickandmorty.ui.screens.characters.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.minarik.rickandmorty.R
import cz.minarik.rickandmorty.common.base.FailedWithError
import cz.minarik.rickandmorty.common.base.Loading
import cz.minarik.rickandmorty.common.base.SuccessWithData
import cz.minarik.rickandmorty.domain.usecase.getcharacterdetail.GetCharacterDetailUseCase
import cz.minarik.rickandmorty.ui.common.hideAllOverlays
import cz.minarik.rickandmorty.ui.common.showError
import cz.minarik.rickandmorty.ui.common.showLoading
import cz.minarik.rickandmorty.ui.common.toDisplayMessage
import cz.minarik.rickandmorty.ui.common.updateData
import cz.minarik.rickandmorty.ui.core.model.ButtonVo
import cz.minarik.rickandmorty.ui.core.model.ErrorIndicatorVo
import cz.minarik.rickandmorty.ui.core.model.StringModel
import cz.minarik.rickandmorty.ui.core.model.UIState
import cz.minarik.rickandmorty.ui.core.model.UIViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
) : UIViewModel<CharacterDetailScreenData, CharacterDetailScreenEvent>, ViewModel() {

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
                    _viewState.updateData {
                        it.copy(
                            character = result.content?.toVo(),
                        )
                    }
                    _viewState.hideAllOverlays()
                }

                is FailedWithError -> {
                    _viewState.showError(
                        error = ErrorIndicatorVo(
                            text = StringModel.String(result.error.toDisplayMessage()),
                            buttonVo = ButtonVo(
                                text = StringModel.Resource(R.string.try_again),
                                onClick = { getCharacterDetail() }
                            ),
                        )
                    )
                }

                is Loading -> {
                    _viewState.showLoading()
                }
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Expand or collapse episodes.
     */
    private fun expandedStateChanged() {
        _viewState.updateData {
            it.copy(
                episodesExpanded = !it.episodesExpanded
            )
        }
    }
}
