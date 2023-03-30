package cz.minarik.alzatest.ui.screens.episodes.detail

import androidx.compose.runtime.mutableStateOf
import cz.minarik.alzatest.common.base.BaseViewModel
import cz.minarik.alzatest.common.base.FailedWithError
import cz.minarik.alzatest.common.base.Loading
import cz.minarik.alzatest.common.base.SuccessWithData
import cz.minarik.alzatest.domain.usecase.getepisodedetail.GetEpisodeDetailUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class EpisodeDetailScreenViewModel(
    private val episodeId: String,
    private val getEpisodeDetailUseCase: GetEpisodeDetailUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(EpisodeDetailScreenState())
    val state: Flow<EpisodeDetailScreenState> = _state

    var expanded = mutableStateOf(false)

    init {
        getEpisodeDetail()
    }

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

    fun expandedStateChanged() {
        expanded.value = !expanded.value
    }
}
