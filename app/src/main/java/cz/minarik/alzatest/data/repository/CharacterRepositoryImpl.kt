package cz.minarik.alzatest.data.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import cz.minarik.alzatest.GetCharacterDetailQuery
import cz.minarik.alzatest.GetCharactersQuery
import cz.minarik.alzatest.data.remote.response.CharactersResponse
import cz.minarik.alzatest.data.remote.response.InfoResponse
import cz.minarik.alzatest.domain.model.TVCharacter
import cz.minarik.alzatest.domain.model.CharacterDetail
import cz.minarik.alzatest.domain.model.Episode
import cz.minarik.alzatest.domain.model.Location
import cz.minarik.alzatest.domain.repository.CharacterRepository
import javax.inject.Inject

/**
 * Implementation of [CharacterRepository].
 *
 * @param apolloClient Apollo client.
 */
class CharacterRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
) : CharacterRepository {

    override suspend fun getCharacters(page: Int): CharactersResponse {
        val result = apolloClient.query(GetCharactersQuery(Optional.presentIfNotNull(page))).execute()
        val characters = result.data?.characters?.results?.mapNotNull { character ->
            character?.id?.let { id ->
                TVCharacter(
                    id = id,
                    name = character.name,
                    imageUrl = character.image
                )
            }
        }.orEmpty()

        val info = result.data?.characters?.info?.let {
            InfoResponse(
                pages = it.pages,
                count = it.count,
                next = it.next,
                prev = it.prev,
            )
        }
        return CharactersResponse(
            characters = characters,
            info = info
        )
    }

    override suspend fun getCharacterDetail(characterId: String): CharacterDetail? {
        val result = apolloClient.query(GetCharacterDetailQuery(characterId)).execute()
        return result.data?.character?.let { character ->
            character.id?.let { id ->
                CharacterDetail(
                    id = id,
                    name = character.name,
                    imageUrl = character.image,
                    species = character.species,
                    status = character.status,
                    gender = character.gender,
                    type = character.type,
                    origin = character.origin?.let {
                        Location(
                            id = it.id ?: "",
                            name = it.name,
                        )
                    },
                    location = character.location?.let {
                        Location(
                            id = it.id ?: "",
                            name = it.name,
                        )
                    },
                    episodes = character.episode.mapNotNull {
                        it?.let {
                            Episode(
                                it.id ?: "",
                                name = it.name,
                            )
                        }
                    },
                )
            }
        }
    }

}
