package cz.minarik.rickandmorty.data.remote.response

import cz.minarik.rickandmorty.domain.model.Episode

/**
 * Response with list of episodes.
 *
 * @param episodes list of episodes
 * @param info info about the response
 */
data class EpisodesResponse(
    val episodes: List<Episode>,
    val info: InfoResponse?,
)
