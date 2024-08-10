package cz.minarik.rickandmorty.ui.screens.episodes.detail

import androidx.compose.runtime.Immutable
import cz.minarik.rickandmorty.ui.model.EpisodeDetailVo

/**
 * State of EpisodeDetailScreen.
 *
 * @property episode Episode detail view object.
 */
@Immutable
data class EpisodeDetailScreenData(
    val episode: EpisodeDetailVo? = null,
)
