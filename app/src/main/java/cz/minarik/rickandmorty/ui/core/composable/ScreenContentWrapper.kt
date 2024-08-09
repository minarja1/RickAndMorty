package cz.minarik.rickandmorty.ui.core.composable

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cz.minarik.rickandmorty.ui.core.model.UIState

/**
 * Wrapper for screen content with loading and error states.
 *
 * @param state UI state
 * @param content screen content
 */
@Composable
fun ScreenContentWrapper(
    state: UIState<*>,
    content: @Composable () -> Unit,
) {
    content()
    Crossfade(
        targetState = state,
        label = "resultScreenAnimation"
    ) { targetState ->
        when {
            targetState.loading -> {
                // todo
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.onBackground,
                )
            }

            targetState.error != null -> {
                // todo
            }
        }
    }
}
