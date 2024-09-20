package cz.minarik.rickandmorty.ui.screens.episodes.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.minarik.rickandmorty.R
import cz.minarik.rickandmorty.common.base.FailedWithError
import cz.minarik.rickandmorty.common.base.Loading
import cz.minarik.rickandmorty.common.base.SuccessWithData
import cz.minarik.rickandmorty.domain.usecase.getepisodedetail.GetEpisodeDetailUseCase
import cz.minarik.rickandmorty.ui.core.model.ButtonVo
import cz.minarik.rickandmorty.ui.core.model.ComposeViewModel
import cz.minarik.rickandmorty.ui.core.model.ErrorIndicatorVo
import cz.minarik.rickandmorty.ui.core.model.StringModel
import cz.minarik.rickandmorty.ui.core.model.UIEvent
import cz.minarik.rickandmorty.ui.core.model.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

/**
 * ViewModel for EpisodeDetailScreen.
 *
 * @property episodeId Id of episode.
 * @property getEpisodeDetailUseCase Use case for getting episode detail.
 */
class EpisodeDetailScreenViewModel(
    private val episodeId: String,
    private val getEpisodeDetailUseCase: GetEpisodeDetailUseCase
) : ComposeViewModel<EpisodeDetailScreenData, UIEvent>, ViewModel() {

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
                    _viewState.update {
                        UIState(
                            data = EpisodeDetailScreenData(
                                episode = result.content?.toVo(),
                            ),
                        )
                    }
                }

                is FailedWithError -> {
                    _viewState.update {
                        UIState(
                            data = it.data,
                            error = ErrorIndicatorVo(
                                text = StringModel.String(result.error),
                                buttonVo = ButtonVo(
                                    text = StringModel.Resource(R.string.try_again),
                                    onClick = { getEpisodeDetail() }
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
        }.launchIn(viewModelScope)
    }
}
