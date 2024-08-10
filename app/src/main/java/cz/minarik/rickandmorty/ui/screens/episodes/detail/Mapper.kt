package cz.minarik.rickandmorty.ui.screens.episodes.detail

import cz.minarik.rickandmorty.domain.model.EpisodeDetail
import cz.minarik.rickandmorty.domain.model.TVCharacter
import cz.minarik.rickandmorty.ui.model.CircleImageVo
import cz.minarik.rickandmorty.ui.model.EpisodeDetailVo

fun EpisodeDetail.toVo() = EpisodeDetailVo(
    id = id,
    name = name,
    code = code,
    airDate = airDate,
    characterImages = characters?.mapNotNull { it.toCircleImageVo() }
)

fun TVCharacter.toCircleImageVo() = imageUrl?.let {
    CircleImageVo(
        id = id,
        imageUrl = imageUrl
    )
}
