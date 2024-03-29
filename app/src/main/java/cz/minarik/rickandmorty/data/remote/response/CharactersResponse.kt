package cz.minarik.rickandmorty.data.remote.response

import cz.minarik.rickandmorty.domain.model.TVCharacter

/**
 * Response with list of characters.
 *
 * @param characters list of characters
 * @param info info about the response
 */
data class CharactersResponse(
    val characters: List<TVCharacter>,
    val info: InfoResponse?,
)
