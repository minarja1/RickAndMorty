package cz.minarik.alzatest.domain.model

data class EpisodeDetail(
    val id: String,
    val name: String?,
    val airDate: String? = null,
    val code: String? = null,
    val characters: List<Character>? = null,
)
