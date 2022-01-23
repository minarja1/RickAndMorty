package cz.minarik.alzatest.ui.screens.home

import cz.minarik.alzatest.domain.model.Category

data class HomeScreenState(
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val error: String = "",
)
