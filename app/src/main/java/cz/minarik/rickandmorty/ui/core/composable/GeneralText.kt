package cz.minarik.rickandmorty.ui.core.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Paragraph
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import cz.minarik.rickandmorty.R
import cz.minarik.rickandmorty.ui.core.model.StringModel

/**
 * General text to be used in components. It can show HTML text if [text] is
 * [StringModel.Html] or [StringModel.HtmlResource] or regular text otherwise.
 *
 * The default [style] uses the [LocalTextStyle] provided by the [MaterialTheme] / components. If
 * you are setting your own style, you may want to consider first retrieving [LocalTextStyle],
 * and using [TextStyle.copy] to keep any theme defined attributes, only modifying the specific
 * attributes you want to override.
 *
 * For ease of use, commonly used parameters from [TextStyle] are also present here. The order of
 * precedence is as follows:
 * - If a parameter is explicitly set here (i.e, it is _not_ `null` or [TextUnit.Unspecified]),
 * then this parameter will always be used.
 * - If a parameter is _not_ set, (`null` or [TextUnit.Unspecified]), then the corresponding value
 * from [style] will be used instead.
 *
 * Additionally, for [color], if [color] is not set, and [style] does not have a color, then
 * [LocalContentColor] will be used with an alpha of [LocalContentAlpha]- this allows this
 * [Text] or element containing this [Text] to adapt to different background colors and still
 * maintain contrast and accessibility.
 *
 * @param text string model to be displayed
 * @param modifier [Modifier] to apply to this layout node.
 * @param color [Color] to apply to the text. If [Color.Unspecified], and [style] has no color set,
 * this will be [LocalContentColor].
 * @param fontSize The size of glyphs to use when painting the text. See [TextStyle.fontSize].
 * @param fontStyle The typeface variant to use when drawing the letters (e.g., italic).
 * See [TextStyle.fontStyle].
 * @param fontWeight The typeface thickness to use when painting the text (e.g., [FontWeight.Bold]).
 * @param fontFamily The font family to be used when rendering the text. See [TextStyle.fontFamily].
 * @param letterSpacing The amount of space to add between each letter.
 * See [TextStyle.letterSpacing].
 * @param textDecoration The decorations to paint on the text (e.g., an underline).
 * See [TextStyle.textDecoration].
 * @param textAlign The alignment of the text within the lines of the paragraph.
 * See [TextStyle.textAlign].
 * @param lineHeight Line height for the [Paragraph] in [TextUnit] unit, e.g. SP or EM.
 * See [TextStyle.lineHeight].
 * @param overflow How visual overflow should be handled.
 * @param softWrap Whether the text should break at soft line breaks. If false, the glyphs in the
 * text will be positioned as if there was unlimited horizontal space. If [softWrap] is false,
 * [overflow] and TextAlign may have unexpected effects.
 * @param maxLines An optional maximum number of lines for the text to span, wrapping if
 * necessary. If the text exceeds the given number of lines, it will be truncated according to
 * [overflow] and [softWrap]. It is required that 1 <= [minLines] <= [maxLines].
 * @param onTextLayout Callback that is executed when a new text layout is calculated. A
 * [TextLayoutResult] object that callback provides contains paragraph information, size of the
 * text, baselines and other details. The callback can be used to add additional decoration or
 * functionality to the text. For example, to draw selection around the text. - only for non-html
 * texts
 * @param style Style configuration for the text such as color, font, line height etc.
 * @param onLinkClick On link click handler - only for HTML texts
 * @param linkStyle Link text style - only for HTML texts
 * @param onClick On click handler - for clickable component above HTML text
 */
@Composable
fun GeneralText(
    text: StringModel,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign = TextAlign.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    onLinkClick: ((String) -> Unit)? = null,
    linkStyle: SpanStyle = style.toSpanStyle().copy(
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Medium
    ),
    onClick: (() -> Unit)? = null,
) {
    when (text) {
        is StringModel.Html,
        is StringModel.HtmlResource,
        is StringModel.ComposedString
            -> {
            HtmlText(
                text = text,
                modifier = modifier,
                style = style.merge(
                    color = color,
                    fontSize = fontSize,
                    fontStyle = fontStyle,
                    fontWeight = fontWeight,
                    fontFamily = fontFamily,
                    letterSpacing = letterSpacing,
                    textDecoration = textDecoration,
                    textAlign = textAlign,
                    lineHeight = lineHeight,
                ),
                softWrap = softWrap,
                overflow = overflow,
                maxLines = maxLines,
                onLinkClick = onLinkClick,
                linkStyle = linkStyle,
                onClick = onClick
            )
        }

        else -> {
            Text(
                text = text.getString(),
                modifier = modifier,
                color = color,
                fontSize = fontSize,
                fontStyle = fontStyle,
                fontWeight = fontWeight,
                fontFamily = fontFamily,
                letterSpacing = letterSpacing,
                textDecoration = textDecoration,
                textAlign = textAlign,
                lineHeight = lineHeight,
                overflow = overflow,
                softWrap = softWrap,
                maxLines = maxLines,
                onTextLayout = onTextLayout,
                style = style,
            )
        }
    }
}

@ComponentPreview
@Composable
fun GeneralTextPreview() {
    PreviewSurface {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            GeneralText(text = StringModel.String("Regular text"))
            GeneralText(text = StringModel.Resource(R.string.app_name))
            GeneralText(
                text = StringModel.HtmlResource(
                    id = R.string.html_resource_text_example,
                    params = arrayOf("123/300")
                )
            )
            GeneralText(
                text = StringModel.ComposedString(
                    models = arrayOf(
                        StringModel.String("Test of GeneralText with composed string"),
                        StringModel.Html("<html><body>with <b>html string</b></body></html>")
                    )
                )
            )
        }
    }
}
