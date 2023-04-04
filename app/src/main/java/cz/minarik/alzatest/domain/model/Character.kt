package cz.minarik.alzatest.domain.model

/**
 * Model for character list item.
 *
 * @property id character id
 * @property name character name
 * @property imageUrl character image url
 */
data class Character(
    val id: String,
    val name: String?,
    val imageUrl: String?,
)
