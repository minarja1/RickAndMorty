package cz.minarik.alzatest.domain.repository

import cz.minarik.alzatest.data.remote.response.EpisodesResponse
import cz.minarik.alzatest.domain.model.EpisodeDetail

interface EpisodeRepository {

    suspend fun getEpisodes(page: Int): EpisodesResponse

    suspend fun getEpisodeDetail(episodeId: String): EpisodeDetail?

}
