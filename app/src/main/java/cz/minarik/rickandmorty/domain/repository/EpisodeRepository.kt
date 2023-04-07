package cz.minarik.rickandmorty.domain.repository

import cz.minarik.rickandmorty.data.remote.response.EpisodesResponse
import cz.minarik.rickandmorty.domain.model.EpisodeDetail

/**
 * Repository for getting data from the network.
 */
interface EpisodeRepository {

    /**
     * Get list of episodes.
     *
     * @param page page number
     * @return list of episodes
     */
    suspend fun getEpisodes(page: Int): EpisodesResponse

    /**
     * Get episode detail.
     *
     * @param episodeId episode id
     * @return episode detail
     */
    suspend fun getEpisodeDetail(episodeId: String): EpisodeDetail?

}
