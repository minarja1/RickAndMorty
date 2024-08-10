package cz.minarik.rickandmorty.ui.model

/**
 * View object for clickable card.
 *
 * @property id Id of card.
 * @property title Title of card.
 * @property subtitle Subtitle of card.
 * @property images List of characters.
 */
data class ClickableCardVo(
    val id: String,
    val title: String?,
    val subtitle: String?,
    val images: List<CircleImageVo> = emptyList(),
)
