package cz.minarik.alzatest.domain.model

/**
 * Model for character detail.
 *
 * @property id character id
 * @property name character name
 * @property imageUrl character image url
 * @property species character species
 * @property type character type
 * @property status character status
 * @property gender character gender
 * @property origin character origin
 * @property location character location
 * @property episodes list of episodes
 */
data class CharacterDetail(
    val id: String,
    val name: String?,
    val imageUrl: String?,
    val species: String?,
    val type: String?,
    val status: String?,
    val gender: String?,
    val origin: Location? = null,
    val location: Location? = null,
    val episodes: List<Episode> = emptyList(),
)
