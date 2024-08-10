package cz.minarik.rickandmorty.ui.core.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * A circular progress indicator with text below it.
 *
 * @param modifier Modifier to be applied to the [Box] container.
 * @param showOverlay indicates if background should be transparent
 */
@Composable
fun ProgressIndicator(
    modifier: Modifier = Modifier,
    showOverlay: Boolean = true,
) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colors.background.copy(
                    alpha = if (showOverlay) .9f else 0f
                )
            )
            .clickable(enabled = false) {},
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.progressSemantics()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(ProgressIndicatorSize),
                color = MaterialTheme.colors.onSurface,
                strokeWidth = ProgressIndicatorStrokeWidth
            )
        }
    }
}

private val ProgressIndicatorSize = 50.dp
private val ProgressIndicatorStrokeWidth = 5.dp

@ComponentPreview
@Composable
private fun ProgressIndicatorPreview() {
    PreviewSurface {
        ProgressIndicator()
    }
}
