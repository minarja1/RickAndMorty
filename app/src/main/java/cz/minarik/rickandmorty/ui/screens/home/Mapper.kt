package cz.minarik.rickandmorty.ui.screens.home

import cz.minarik.rickandmorty.domain.model.TVCharacter
import cz.minarik.rickandmorty.ui.model.TVCharacterVo

/**
 * Mapper for converting [TVCharacter] to [TVCharacterVo].
 */
fun TVCharacter.toVo() = TVCharacterVo(
    id = id,
    name = name,
    imageUrl = imageUrl,
)
