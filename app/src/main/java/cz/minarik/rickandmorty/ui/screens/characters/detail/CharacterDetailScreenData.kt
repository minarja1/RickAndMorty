package cz.minarik.rickandmorty.ui.screens.characters.detail

import cz.minarik.rickandmorty.domain.model.CharacterDetail

/**
 * State of CharacterDetailScreen.
 *
 * @property character Character detail.
 * @property episodesExpanded Whether episodes are expanded.
 */
data class CharacterDetailScreenData(
    val character: CharacterDetail? = null,
    val episodesExpanded: Boolean = false,
)
