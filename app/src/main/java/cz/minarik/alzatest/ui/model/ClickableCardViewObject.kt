package cz.minarik.alzatest.ui.model

import cz.minarik.alzatest.domain.model.Character
import cz.minarik.alzatest.domain.model.Episode


data class ClickableCardViewObject(
    val id: String,
    val title: String? = null,
    val subtitle: String?,
    val characters: List<Character> = emptyList(),
)

fun Episode.toCardVO(): ClickableCardViewObject = ClickableCardViewObject(
    id = id,
    title = name,
    subtitle = code,
    characters = characters ?: emptyList(),
)
