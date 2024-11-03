package cz.minarik.rickandmorty.ui.model

import androidx.compose.runtime.Immutable

/**
 * View object for circle image.
 **/
@Immutable
data class CircleImageVo(
    val id: String,
    val imageUrl: String,
)
