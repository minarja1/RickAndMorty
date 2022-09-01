package cz.minarik.alzatest.domain.repository

import cz.minarik.alzatest.domain.model.Character

interface CharacterRepository {

    suspend fun getCharacters(): List<Character>

}