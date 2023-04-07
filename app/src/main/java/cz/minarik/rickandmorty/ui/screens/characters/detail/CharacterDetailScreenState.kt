package cz.minarik.rickandmorty.ui.screens.characters.detail

import cz.minarik.rickandmorty.domain.model.CharacterDetail

/**
 * State of CharacterDetailScreen.
 *
 * @property isLoading True if loading is in progress.
 * @property character Character detail.
 * @property error Error message.
 */
data class CharacterDetailScreenState(
    val isLoading: Boolean = false,
    val character: CharacterDetail? = null,
    val error: String = "",
)
