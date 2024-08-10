package cz.minarik.rickandmorty.ui.screens.home

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import cz.minarik.rickandmorty.ui.model.ClickableCardVo
import cz.minarik.rickandmorty.ui.model.TVCharacterVo
import kotlinx.coroutines.flow.Flow

/**
 * State of HomeScreen.
 *
 * @property pagedCharacters Paged characters.
 * @property pagedEpisodes Paged episodes.
 */
@Immutable
data class HomeScreenData(
    val pagedCharacters: Flow<PagingData<TVCharacterVo>>,
    val pagedEpisodes: Flow<PagingData<ClickableCardVo>>,
)
