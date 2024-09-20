package cz.minarik.rickandmorty.ui.core.composable

import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.style.BulletSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.text.style.URLSpan
import android.text.style.UnderlineSpan
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.em
import androidx.core.text.HtmlCompat
import cz.minarik.rickandmorty.ui.core.model.StringModel

/**
 * Html text converts html strings to jetpack compose text view
 *
 * @param text The text to be displayed.
 * @param modifier Modifier to apply to this layout node.
 * @param style Style configuration for the text such as  font, line height etc.
 * @param softWrap Whether the text should break at soft line breaks. If false, the glyphs in the text will be positioned as if there was unlimited horizontal space. If softWrap is false, overflow and TextAlign may have unexpected effects.
 * @param overflow How visual overflow should be handled.
 * @param maxLines An optional maximum number of lines for the text to span, wrapping if necessary. If the text exceeds the given number of lines, it will be truncated according to [overflow] and [softWrap]. If it is not null, then it must be greater than zero.
 * @param onLinkClick On link click handler.
 * @param linkStyle Link text style no need to specify, if there is not specified style it uses [style] and change its color and font weight to make it different against to regular text
 * @param flag Wrapping of the text
 * @param onClick On click handler.
 */
@Composable
fun HtmlText(
    text: StringModel,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    onLinkClick: ((String) -> Unit)? = null,
    linkStyle: SpanStyle = style.toSpanStyle().copy(
        color = MaterialTheme.colors.primary,
        fontWeight = FontWeight.Medium,
    ),
    flag: Int = HtmlCompat.FROM_HTML_MODE_COMPACT,
    onClick: (() -> Unit)? = null,
) {
    val content = if (text is StringModel.ComposedString) {
        buildAnnotatedString {
            text.models.forEachIndexed { index, model ->
                when (model) {
                    is StringModel.Html,
                    is StringModel.HtmlResource,
                    -> append(model.getString().asHTML(flag, linkStyle))

                    else -> append(model.getString())
                }

                if (index < text.models.size - 1) {
                    append(text.separator)
                }
            }
        }
    } else text.getString().asHTML(flag, linkStyle)

    // todo resolve deprecated
    ClickableText(
        modifier = modifier,
        text = content,
        style = style,
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines,
        onClick = { offset ->
            content.getStringAnnotations(URL_TAG, offset, offset)
                .firstOrNull {
                    it.start <= offset && offset <= it.end
                }?.let { stringAnnotation ->
                    onLinkClick?.invoke(stringAnnotation.item)
                } ?: run {
                onClick?.invoke()
            }
        }
    )
}

@ComponentPreview
@Composable
fun HtmlTextPreview() = PreviewSurface {
    HtmlText(text = StringModel.String("abc"))
}

/**
 * Converts html string to annotated string
 * @param flag html flag.
 * @param linkStyle Link text style.
 */
private fun String.asHTML(
    flag: Int,
    linkStyle: SpanStyle,
) = buildAnnotatedString {
    val spanned = HtmlCompat.fromHtml(this@asHTML, flag) as SpannableStringBuilder

    spanned.getSpans(0, spanned.length, BulletSpan::class.java).forEach { span ->
        val startIndex = spanned.getSpanStart(span)
        spanned.insert(startIndex, "\u2022 ") // add a bullet for bullet spans
    }

    val spans = spanned.getSpans(0, spanned.length, Any::class.java)
    append(spanned.toString())
    spans
        .filter { it !is BulletSpan }
        .forEach { span ->
            val start = spanned.getSpanStart(span)
            val end = spanned.getSpanEnd(span)
            when (span) {
                is StyleSpan -> span.spanStyle()
                is UnderlineSpan -> span.spanStyle()
                is TypefaceSpan -> span.spanStyle()
                is ForegroundColorSpan -> span.spanStyle()
                is URLSpan -> {
                    addStringAnnotation(
                        tag = URL_TAG,
                        annotation = span.url,
                        start = start,
                        end = end
                    )
                    linkStyle
                }

                is RelativeSizeSpan -> span.spanStyle()

                else -> {
                    null
                }
            }?.let { spanStyle ->
                addStyle(spanStyle, start, end)
            }
        }
}

/**
 * returns link span style with underlined text
 */
internal fun UnderlineSpan.spanStyle(): SpanStyle = SpanStyle(
    textDecoration = TextDecoration.Underline
)

/**
 * returns span style with specified font weight
 */
internal fun StyleSpan.spanStyle(): SpanStyle =
    when (style) {
        Typeface.BOLD -> SpanStyle(fontWeight = FontWeight.Bold)
        Typeface.ITALIC -> SpanStyle(fontStyle = FontStyle.Italic)
        else -> SpanStyle(
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic
        )
    }

/**
 * returns span style with Roboto font family
 */
internal fun TypefaceSpan.spanStyle(): SpanStyle = SpanStyle(
    fontFamily = FontFamily.SansSerif
)

/**
 * returns span style with text color
 */
internal fun ForegroundColorSpan.spanStyle(): SpanStyle =
    SpanStyle(color = Color(foregroundColor))

/**
 * returns span style with resized text
 */
internal fun RelativeSizeSpan.spanStyle(): SpanStyle =
    SpanStyle(fontSize = this.sizeChange.em)

private const val URL_TAG = "url_tag"
