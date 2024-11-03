package cz.minarik.rickandmorty.ui.screens.characters.detail

import cz.minarik.rickandmorty.domain.model.CharacterDetail
import cz.minarik.rickandmorty.ui.common.toCardVO
import cz.minarik.rickandmorty.ui.model.CharacterDetailVo

/**
 * Mapper for converting [CharacterDetail] to [CharacterDetailVo].
 */
fun CharacterDetail.toVo() = CharacterDetailVo(
    id = id,
    name = name,
    status = status,
    species = species,
    gender = gender,
    origin = origin?.name,
    location = location?.name,
    imageUrl = imageUrl,
    episodes = episodes.map { it.toCardVO() },
    type = type,
)
