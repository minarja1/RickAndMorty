package cz.minarik.alzatest.data.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import cz.minarik.alzatest.GetCharacterDetailQuery
import cz.minarik.alzatest.GetCharactersQuery
import cz.minarik.alzatest.data.remote.response.CharactersInfo
import cz.minarik.alzatest.data.remote.response.CharactersResponse
import cz.minarik.alzatest.domain.model.Character
import cz.minarik.alzatest.domain.model.CharacterDetail
import cz.minarik.alzatest.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
) : CharacterRepository {

    override suspend fun getCharacters(page: Int): CharactersResponse {
        val result = apolloClient.query(GetCharactersQuery(Optional.presentIfNotNull(page))).execute()
        val characters = result.data?.characters?.results?.mapNotNull { character ->
            character?.id?.let {
                Character(
                    id = it,
                    name = character.name,
                    imageUrl = character.image
                )
            }
        }.orEmpty()

        val info = result.data?.characters?.info?.let {
            CharactersInfo(
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
            character.id?.let {
                CharacterDetail(
                    id = character.id,
                    name = character.name,
                    imageUrl = character.image,
                    species = character.species
                )
            }
        }
    }

}
