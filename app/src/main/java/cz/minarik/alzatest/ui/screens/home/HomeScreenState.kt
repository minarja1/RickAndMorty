package cz.minarik.alzatest.ui.screens.home

import cz.minarik.alzatest.domain.model.Character

data class HomeScreenState(
    val isLoading: Boolean = false,
    val characters: List<Character> = emptyList(),
    val error: String = "",
)
