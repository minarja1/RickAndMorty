package cz.minarik.alzatest.domain.model

data class Episode(
    val id: String,
    val name: String?,
    val code: String? = null,
    val characters: List<Character>? = null,
)
