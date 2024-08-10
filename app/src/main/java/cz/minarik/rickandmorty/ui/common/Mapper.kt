package cz.minarik.rickandmorty.ui.common

import cz.minarik.rickandmorty.domain.model.Episode
import cz.minarik.rickandmorty.ui.model.ClickableCardVo
import cz.minarik.rickandmorty.ui.screens.episodes.detail.toCircleImageVo


/**
 * Convert [Episode] to [ClickableCardVo].
 */
fun Episode.toCardVO(): ClickableCardVo = ClickableCardVo(
    id = id,
    title = name,
    subtitle = code,
    images = characters?.mapNotNull { it.toCircleImageVo() }.orEmpty()
)
