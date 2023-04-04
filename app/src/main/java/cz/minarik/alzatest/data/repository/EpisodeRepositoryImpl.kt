package cz.minarik.alzatest.data.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import cz.minarik.alzatest.GetEpisodeDetailQuery
import cz.minarik.alzatest.GetEpisodesQuery
import cz.minarik.alzatest.data.remote.response.EpisodesResponse
import cz.minarik.alzatest.data.remote.response.InfoResponse
import cz.minarik.alzatest.domain.model.Character
import cz.minarik.alzatest.domain.model.Episode
import cz.minarik.alzatest.domain.model.EpisodeDetail
import cz.minarik.alzatest.domain.repository.EpisodeRepository
import javax.inject.Inject

/**
 * Implementation of [EpisodeRepository].
 *
 * @param apolloClient Apollo client.
 */
class EpisodeRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
) : EpisodeRepository {

    override suspend fun getEpisodes(page: Int): EpisodesResponse {
        val result = apolloClient.query(GetEpisodesQuery(Optional.presentIfNotNull(page))).execute()
        val episodes = result.data?.episodes?.results?.mapNotNull { episode ->
            episode?.id?.let {
                Episode(
                    id = it,
                    name = episode.name,
                    code = episode.episode,
                    characters = episode.characters.mapNotNull { character ->
                        character?.id?.let { id ->
                            Character(
                                id = id,
                                name = character.name,
                                imageUrl = character.image,
                            )
                        }
                    }
                )
            }
        }.orEmpty()

        val info = result.data?.episodes?.info?.let {
            InfoResponse(
                pages = it.pages,
                count = it.count,
                next = it.next,
                prev = it.prev,
            )
        }
        return EpisodesResponse(
            episodes = episodes,
            info = info
        )
    }

    override suspend fun getEpisodeDetail(episodeId: String): EpisodeDetail? {
        val result = apolloClient.query(GetEpisodeDetailQuery(episodeId)).execute()

        return result.data?.episode?.let { episode ->
            episode.id?.let {
                EpisodeDetail(
                    id = it,
                    name = episode.name,
                    code = episode.episode,
                    airDate = episode.air_date,
                    characters = episode.characters.mapNotNull { character ->
                        character?.id?.let { id ->
                            Character(
                                id = id,
                                name = character.name,
                                imageUrl = character.image,
                            )
                        }
                    }
                )
            }
        }
    }
}
