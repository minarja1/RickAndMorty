package cz.minarik.rickandmorty.ui.model

import androidx.compose.runtime.Immutable

/**
 * Model for character list item.
 *
 * @property id character id
 * @property name character name
 * @property imageUrl character image url
 */
@Immutable
data class TVCharacterVo(
    val id: String,
    val name: String?,
    val imageUrl: String?,
)
