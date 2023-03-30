package cz.minarik.alzatest.ui.screens.characters.detail

import cz.minarik.alzatest.domain.model.CharacterDetail

data class CharacterDetailScreenState(
    val isLoading: Boolean = false,
    val character: CharacterDetail? = null,
    val error: String = "",
)
