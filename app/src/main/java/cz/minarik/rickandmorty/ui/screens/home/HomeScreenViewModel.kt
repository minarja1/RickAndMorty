package cz.minarik.rickandmorty.ui.screens.home

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import cz.minarik.rickandmorty.common.base.BaseViewModel
import cz.minarik.rickandmorty.data.remote.paging.CharacterPagingSource
import cz.minarik.rickandmorty.data.remote.paging.EpisodePagingSource
import cz.minarik.rickandmorty.domain.model.TVCharacter
import cz.minarik.rickandmorty.domain.model.Episode
import cz.minarik.rickandmorty.domain.repository.CharacterRepository
import cz.minarik.rickandmorty.domain.repository.EpisodeRepository
import kotlinx.coroutines.flow.Flow

/**
 * ViewModel for HomeScreen.
 *
 * @property characterRepository Repository for characters.
 * @property episodeRepository Repository for episodes.
 */
class HomeScreenViewModel(
    private val characterRepository: CharacterRepository,
    private val episodeRepository: EpisodeRepository,
) : BaseViewModel() {

    /**
     * Flow of paged characters.
     */
    val pagedCharacters: Flow<PagingData<TVCharacter>> = Pager(PagingConfig(pageSize = 20)) {
        CharacterPagingSource(characterRepository)
    }.flow.cachedIn(viewModelScope)

    /**
     * Flow of paged episodes.
     */
    val pagedEpisodes: Flow<PagingData<Episode>> = Pager(PagingConfig(pageSize = 20)) {
        EpisodePagingSource(episodeRepository)
    }.flow.cachedIn(viewModelScope)
}
