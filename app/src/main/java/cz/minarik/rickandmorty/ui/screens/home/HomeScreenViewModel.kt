package cz.minarik.rickandmorty.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import cz.minarik.rickandmorty.data.remote.paging.CharacterPagingSource
import cz.minarik.rickandmorty.data.remote.paging.EpisodePagingSource
import cz.minarik.rickandmorty.domain.repository.CharacterRepository
import cz.minarik.rickandmorty.domain.repository.EpisodeRepository
import cz.minarik.rickandmorty.ui.common.toCardVO
import cz.minarik.rickandmorty.ui.core.model.UIViewModel
import cz.minarik.rickandmorty.ui.core.model.UIEvent
import cz.minarik.rickandmorty.ui.core.model.UIState
import cz.minarik.rickandmorty.ui.model.ClickableCardVo
import cz.minarik.rickandmorty.ui.model.TVCharacterVo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

/**
 * ViewModel for HomeScreen.
 *
 * @property characterRepository Repository for characters.
 * @property episodeRepository Repository for episodes.
 */
class HomeScreenViewModel(
    private val characterRepository: CharacterRepository,
    private val episodeRepository: EpisodeRepository,
) : UIViewModel<HomeScreenData, UIEvent>, ViewModel() {

    /**
     * Flow of paged characters.
     */
    private val pagedCharacters: Flow<PagingData<TVCharacterVo>> =
        Pager(PagingConfig(pageSize = 20)) {
            CharacterPagingSource(characterRepository)
        }.flow.map { pagingData ->
            pagingData.map { character ->
                character.toVo()
            }
        }.cachedIn(viewModelScope)

    /**
     * Flow of paged episodes.
     */
    private val pagedEpisodes: Flow<PagingData<ClickableCardVo>> =
        Pager(PagingConfig(pageSize = 20)) {
            EpisodePagingSource(episodeRepository)
        }.flow.map { pagingData ->
            pagingData.map { episode ->
                episode.toCardVO()
            }
        }.cachedIn(viewModelScope)

    private val _viewState = MutableStateFlow(
        UIState(
            data = HomeScreenData(
                pagedCharacters = pagedCharacters,
                pagedEpisodes = pagedEpisodes,
            ),
        )
    )

    override val viewState: StateFlow<UIState<HomeScreenData>> =
        _viewState.asStateFlow()

    override fun onEvent(event: UIEvent) {
        // No events to handle
    }
}
