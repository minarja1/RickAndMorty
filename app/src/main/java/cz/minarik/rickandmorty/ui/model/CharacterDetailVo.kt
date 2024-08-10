package cz.minarik.rickandmorty.ui.model

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
data class CharacterDetailVo(
    val id: String,
    val name: String?,
    val imageUrl: String?,
    val species: String?,
    val type: String?,
    val status: String?,
    val gender: String?,
    val origin: String? = null,
    val location: String? = null,
    val episodes: List<ClickableCardVo> = emptyList(),
)
