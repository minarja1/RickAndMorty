package cz.minarik.alzatest.data.remote.response

import cz.minarik.alzatest.domain.model.Character

data class CharactersResponse(
    val characters: List<Character>,
    val info: CharactersInfo?,
)

data class CharactersInfo(
    /**
     * The amount of pages.
     */

    val pages: Int?,
    /**
     * The length of the response.
     */

    val count: Int?,
    /**
     * Number of the next page (if it exists)
     */
    val next: Int?,

    /**
     * Number of the previous page (if it exists)
     */
    val prev: Int?,
)
