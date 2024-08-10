package cz.minarik.rickandmorty.ui.screens.characters.detail

import androidx.compose.runtime.Immutable
import cz.minarik.rickandmorty.ui.model.CharacterDetailVo

/**
 * State of CharacterDetailScreen.
 *
 * @property character Character detail.
 * @property episodesExpanded Whether episodes are expanded.
 */
@Immutable
data class CharacterDetailScreenData(
    val character: CharacterDetailVo? = null,
    val episodesExpanded: Boolean = false,
)
