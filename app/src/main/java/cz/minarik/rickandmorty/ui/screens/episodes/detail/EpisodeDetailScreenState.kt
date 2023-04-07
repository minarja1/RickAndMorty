package cz.minarik.rickandmorty.ui.screens.episodes.detail

import cz.minarik.rickandmorty.domain.model.EpisodeDetail

/**
 * State of EpisodeDetailScreen.
 *
 * @property isLoading True if loading is in progress.
 * @property episode Episode detail.
 * @property error Error message.
 */
data class EpisodeDetailScreenState(
    val isLoading: Boolean = false,
    val episode: EpisodeDetail? = null,
    val error: String = "",
)
