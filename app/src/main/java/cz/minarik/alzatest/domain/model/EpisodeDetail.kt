package cz.minarik.alzatest.domain.model

/**
 * Model for episode detail.
 *
 * @param id episode id
 * @param name episode name
 * @param airDate air date
 * @param code episode code
 * @param characters list of characters
 */
data class EpisodeDetail(
    val id: String,
    val name: String?,
    val airDate: String? = null,
    val code: String? = null,
    val characters: List<Character>? = null,
)
