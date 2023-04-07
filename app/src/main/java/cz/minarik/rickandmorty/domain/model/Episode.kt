package cz.minarik.rickandmorty.domain.model

/**
 * Model for episode.
 *
 * @param id episode id
 * @param name episode name
 * @param code episode code
 * @param characters list of characters
 */
data class Episode(
    val id: String,
    val name: String?,
    val code: String? = null,
    val characters: List<TVCharacter>? = null,
)
