package cz.minarik.rickandmorty.ui.screens.episodes.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.minarik.rickandmorty.R
import cz.minarik.rickandmorty.common.base.FailedWithError
import cz.minarik.rickandmorty.common.base.Loading
import cz.minarik.rickandmorty.common.base.SuccessWithData
import cz.minarik.rickandmorty.domain.usecase.getepisodedetail.GetEpisodeDetailUseCase
import cz.minarik.rickandmorty.ui.common.hideAllOverlays
import cz.minarik.rickandmorty.ui.common.showError
import cz.minarik.rickandmorty.ui.common.showLoading
import cz.minarik.rickandmorty.ui.common.updateData
import cz.minarik.rickandmorty.ui.core.model.ButtonVo
import cz.minarik.rickandmorty.ui.core.model.ErrorIndicatorVo
import cz.minarik.rickandmorty.ui.core.model.StringModel
import cz.minarik.rickandmorty.ui.core.model.UIEvent
import cz.minarik.rickandmorty.ui.core.model.UIState
import cz.minarik.rickandmorty.ui.core.model.UIViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * ViewModel for EpisodeDetailScreen.
 *
 * @property episodeId Id of episode.
 * @property getEpisodeDetailUseCase Use case for getting episode detail.
 */
class EpisodeDetailScreenViewModel(
    private val episodeId: String,
    private val getEpisodeDetailUseCase: GetEpisodeDetailUseCase
) : UIViewModel<EpisodeDetailScreenData, UIEvent>, ViewModel() {

    private val _viewState = MutableStateFlow(
        UIState(
            data = EpisodeDetailScreenData(),
        )
    )

    override val viewState: StateFlow<UIState<EpisodeDetailScreenData>> =
        _viewState.asStateFlow()

    init {
        getEpisodeDetail()
    }

    override fun onEvent(event: UIEvent) {
        // No events to handle
    }

    /**
     * Get episode detail.
     *
     * Use [viewState] to observe the result.
     */
    private fun getEpisodeDetail() {
        getEpisodeDetailUseCase(episodeId).onEach { result ->
            when (result) {
                is SuccessWithData -> {
                    _viewState.updateData {
                        it.copy(
                            episode = result.content?.toVo(),
                        )
                    }
                    _viewState.hideAllOverlays()
                }

                is FailedWithError -> {
                    _viewState.showError(
                        error = ErrorIndicatorVo(
                            text = StringModel.String(result.error),
                            buttonVo = ButtonVo(
                                text = StringModel.Resource(R.string.try_again),
                                onClick = { getEpisodeDetail() }
                            ),
                        ),
                    )
                }

                is Loading -> {
                    _viewState.showLoading()
                }
            }
        }.launchIn(viewModelScope)
    }
}
