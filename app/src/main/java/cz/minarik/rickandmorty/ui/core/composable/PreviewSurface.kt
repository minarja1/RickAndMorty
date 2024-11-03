package cz.minarik.rickandmorty.ui.core.composable

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cz.minarik.rickandmorty.ui.theme.RaMTheme

/**
 * Preview surface to help display composables in dark mode. It also uses [isSystemInDarkTheme]
 * to support darkmode in showkase since our app uses custom implementation.
 *
 * @param modifier to be applied on this layout
 * @param color lambda, that returns background color for the surface
 * @param content to be displayed in previews
 */
@Composable
fun PreviewSurface(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    RaMTheme(
        isDarkTheme = isSystemInDarkTheme(),
    ) {
        Surface(
            modifier = modifier,
        ) {
            content()
        }
    }
}
