package cz.minarik.alzatest.domain.model

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
