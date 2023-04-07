package cz.minarik.rickandmorty.ui.screens.home.util

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Utils object for character item
 */
object CharacterItemUtils {

    private val IdealWidth = 168.dp
    private const val MinimalNumberOfColumns = 2

    /**
     * Gets optimal column count for characters grid.
     *
     * @param availableWidth available width
     *
     * @return Number of columns that is ideal for current screen.
     */
    fun getListColumnsCount(availableWidth: Dp): Int {
        return (availableWidth / IdealWidth)
            .toInt()
            .coerceAtLeast(MinimalNumberOfColumns)
    }
}
