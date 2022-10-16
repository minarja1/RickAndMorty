package cz.minarik.alzatest.domain.repository

import cz.minarik.alzatest.data.remote.response.EpisodesResponse

interface EpisodeRepository {

    suspend fun getEpisodes(page: Int): EpisodesResponse

}
