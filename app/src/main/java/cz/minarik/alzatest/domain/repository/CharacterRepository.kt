package cz.minarik.alzatest.domain.repository

import cz.minarik.alzatest.data.remote.response.CharactersResponse
import cz.minarik.alzatest.domain.model.CharacterDetail
import cz.minarik.alzatest.domain.model.EpisodeDetail

interface CharacterRepository {

    suspend fun getCharacters(page: Int): CharactersResponse

    suspend fun getCharacterDetail(characterId: String): CharacterDetail?

}
