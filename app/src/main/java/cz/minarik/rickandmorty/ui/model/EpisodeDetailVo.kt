package cz.minarik.rickandmorty.ui.model

import androidx.compose.runtime.Immutable

/**
 * Model for character detail.
 *
 **/
@Immutable
data class EpisodeDetailVo(
    val id: String,
    val name: String?,
    val airDate: String? = null,
    val code: String? = null,
    val characterImages: List<CircleImageVo>? = null,
)
