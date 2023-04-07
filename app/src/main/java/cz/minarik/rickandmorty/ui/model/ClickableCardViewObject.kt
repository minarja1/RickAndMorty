package cz.minarik.rickandmorty.ui.model

import cz.minarik.rickandmorty.domain.model.TVCharacter
import cz.minarik.rickandmorty.domain.model.Episode

/**
 * View object for clickable card.
 *
 * @property id Id of card.
 * @property title Title of card.
 * @property subtitle Subtitle of card.
 * @property characters List of characters.
 */
data class ClickableCardViewObject(
    val id: String,
    val title: String? = null,
    val subtitle: String?,
    val characters: List<TVCharacter> = emptyList(),
)

/**
 * Convert [Episode] to [ClickableCardViewObject].
 */
fun Episode.toCardVO(): ClickableCardViewObject = ClickableCardViewObject(
    id = id,
    title = name,
    subtitle = code,
    characters = characters ?: emptyList(),
)
