package cz.minarik.alzatest.ui.screens.episodes.detail

import cz.minarik.alzatest.domain.model.Episode
import cz.minarik.alzatest.domain.model.EpisodeDetail


data class EpisodeDetailScreenState(
    val isLoading: Boolean = false,
    val episode: EpisodeDetail? = null,
    val error: String = "",
)
