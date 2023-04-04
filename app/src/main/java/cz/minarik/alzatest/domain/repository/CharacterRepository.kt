package cz.minarik.alzatest.domain.repository

import cz.minarik.alzatest.data.remote.response.CharactersResponse
import cz.minarik.alzatest.domain.model.CharacterDetail

/**
 * Repository for getting data from the network.
 */
interface CharacterRepository {

    /**
     * Get list of characters.
     *
     * @param page page number
     * @return list of characters
     */
    suspend fun getCharacters(page: Int): CharactersResponse

    /**
     * Get character detail.
     *
     * @param characterId character id
     * @return character detail
     */
    suspend fun getCharacterDetail(characterId: String): CharacterDetail?

}
