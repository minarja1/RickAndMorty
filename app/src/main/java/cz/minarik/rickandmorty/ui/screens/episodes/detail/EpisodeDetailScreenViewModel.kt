package cz.minarik.rickandmorty.ui.screens.episodes.detail

import cz.minarik.rickandmorty.common.base.BaseViewModel
import cz.minarik.rickandmorty.common.base.FailedWithError
import cz.minarik.rickandmorty.common.base.Loading
import cz.minarik.rickandmorty.common.base.SuccessWithData
import cz.minarik.rickandmorty.domain.usecase.getepisodedetail.GetEpisodeDetailUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
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
) : BaseViewModel() {

    private val _state = MutableStateFlow(EpisodeDetailScreenState())

    /**
     * State of EpisodeDetailScreen.
     */
    val state: Flow<EpisodeDetailScreenState> = _state

    init {
        getEpisodeDetail()
    }

    /**
     * Get episode detail.
     *
     * Use [state] to observe the result.
     */
    fun getEpisodeDetail() {
        getEpisodeDetailUseCase(episodeId).onEach { result ->
            when (result) {
                is SuccessWithData -> {
                    _state.value = EpisodeDetailScreenState(
                        episode = result.content,
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
