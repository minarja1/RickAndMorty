package cz.minarik.rickandmorty.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Charcoal,
    primaryVariant = Charcoal,
    secondary = Charcoal,

    background = Color.Black,
)

private val LightColorPalette = lightColors(
    primary = Cultured,
    primaryVariant = Cultured,
    secondary = Cultured,

    background = Color.White,
)

@Composable
fun RaMTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (isDarkTheme) DarkColorPalette else LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
