package cz.minarik.alzatest.data.remote.response

import cz.minarik.alzatest.domain.model.Character

data class CharactersResponse(
    val characters: List<Character>,
    val info: InfoResponse?,
)
