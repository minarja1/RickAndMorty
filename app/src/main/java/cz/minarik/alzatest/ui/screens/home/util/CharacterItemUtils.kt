package cz.minarik.alzatest.ui.screens.home.util

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Utils object for catalogue item
 */
object CharacterItemUtils {

    private val IdealWidth = 168.dp
    private const val MinimalNumberOfColumns = 2

    /**
     * Gets optimal column count for products grid.
     *
     * @param availableWidth available width for products.
     *
     * @return Number of columns that is ideal for current screen.
     */
    fun getListColumnsCount(availableWidth: Dp): Int {
        return (availableWidth / IdealWidth)
            .toInt()
            .coerceAtLeast(MinimalNumberOfColumns)
    }
}
