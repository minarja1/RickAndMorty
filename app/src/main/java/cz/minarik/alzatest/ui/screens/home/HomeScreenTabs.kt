package cz.minarik.alzatest.ui.screens.home

import cz.minarik.alzatest.R

/**
 * Enum class representing tabs on the home screen.
 *
 * @property tabTitleStringRes String resource for the title of the tab.
 */
enum class HomeScreenTabs(
    val tabTitleStringRes: Int,
) {
    Characters(R.string.characters),
    Episodes(R.string.episodes),
}
