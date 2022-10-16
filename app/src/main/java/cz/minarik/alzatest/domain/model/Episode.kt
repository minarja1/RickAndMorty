package cz.minarik.alzatest.domain.model

data class Episode(
    val id: String,
    val name: String?,
    val air_date: String? = null,
    val code: String? = null,
    val characters: List<Character>? = null,
)
