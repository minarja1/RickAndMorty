package cz.minarik.rickandmorty.ui.model

/**
 * Model for episode.
 *
 * @param id episode id
 * @param name episode name
 * @param code episode code
 * @param characters list of characters
 */
data class EpisodeVo(
    val id: String,
    val name: String?,
    val code: String? = null,
    val characters: List<TVCharacterVo>? = null,
)
