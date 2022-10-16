package cz.minarik.alzatest.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Color definitions.
 */


@Suppress("MagicNumber")
val Charcoal = Color(0xff373F51)

@Suppress("MagicNumber")
val CardViewOutlineColor = Color(0x33FFFFFF)

internal val Cultured = Color(0xFFF8F8F8)
internal val AntiFlashWhite = Color(0xFFF2F3F3)
internal val DarkSilver = Color(0xFF707070)
internal val EerieBlack = Color(0xFF171B1E)
internal val Placeholder = Color(0xFFE3E5E7)
internal val Border = Color(0xFFE8E8E8)
internal val Headline = Color(0xFF252425)

private object LightGrayscale : Grayscale {
    override val gray0 = Color.White
    override val gray100 = Border
    override val gray200 = Cultured
    override val gray300 = AntiFlashWhite
    override val gray400 = Placeholder
    override val gray700 = DarkSilver
    override val gray800 = Headline
    override val gray900 = EerieBlack
}

/**
 * Additional grayscale colors.
 * Accessible from [Colors.grayscale].
 *
 * Contains the properties from light to dark gray colors.
 * Lighter colors has lower suffix value, darker colors has higher suffix value.
 */
@Suppress("UndocumentedPublicProperty") // See class description
interface Grayscale {
    val gray0: Color
    val gray100: Color
    val gray200: Color
    val gray300: Color
    val gray400: Color
    val gray700: Color
    val gray800: Color
    val gray900: Color
}

private val LocalGrayscale: ProvidableCompositionLocal<Grayscale> = staticCompositionLocalOf { LightGrayscale }

/**
 * Returns the [Grayscale] colors.
 */
val Colors.grayscale: Grayscale
    @Composable
    @ReadOnlyComposable
    get() = LocalGrayscale.current

