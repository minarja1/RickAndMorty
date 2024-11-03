package cz.minarik.rickandmorty.ui.core.composable

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import cz.minarik.rickandmorty.R
import cz.minarik.rickandmorty.ui.common.toDisplayMessage
import cz.minarik.rickandmorty.ui.core.model.ButtonVo
import cz.minarik.rickandmorty.ui.core.model.ErrorIndicatorVo
import cz.minarik.rickandmorty.ui.core.model.StringModel
import cz.minarik.rickandmorty.ui.core.model.UIState

/**
 * Wrapper for screen content with loading and error states.
 *
 * @param state UI state
 * @param showLoading show loading indicator
 * @param content screen content
 */
@Composable
fun ScreenContentWrapper(
    state: UIState<*>,
    showLoading: Boolean = true,
    content: @Composable () -> Unit,
) {
    content()
    Crossfade(
        targetState = state,
        label = "resultScreenAnimation"
    ) { targetState ->
        when {
            targetState.loading && showLoading -> {
                ProgressIndicator(
                    modifier = Modifier.fillMaxSize(),
                )
            }

            targetState.error != null -> {
                ErrorIndicator(
                    modifier = Modifier.fillMaxSize(),
                    errorIndicatorVo = targetState.error,
                )
            }
        }
    }
}

/**
 * Wrapper for screen content based on paged load state
 *
 * @param loadState Paged load state
 * @param onTryAgain Callback for retry button
 * @param content screen content
 */
@Composable
fun PagedScreenContentWrapper(
    loadState: LoadState,
    onTryAgain: () -> Unit,
    content: @Composable () -> Unit,
) {
    content()
    Crossfade(
        targetState = loadState,
        label = "resultScreenAnimation"
    ) { targetState ->
        when (targetState) {
            is LoadState.Loading -> {
                ProgressIndicator(
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is LoadState.Error -> {
                (loadState as? LoadState.Error)?.let {
                    ErrorIndicator(
                        modifier = Modifier.fillMaxSize(),
                        errorIndicatorVo = ErrorIndicatorVo(
                            text = StringModel.String(loadState.error.toDisplayMessage()),
                            buttonVo = ButtonVo(
                                text = StringModel.Resource(id = R.string.try_again),
                                onClick = onTryAgain
                            ),
                        ),
                    )
                }
            }

            is LoadState.NotLoading -> Unit
        }
    }
}

@ComponentPreview
@Composable
private fun ScreenContentWrapperLoadingPreview() {
    PreviewSurface {
        ScreenContentWrapper(
            state = UIState(loading = true, data = null),
            content = {
                Column {
                    Text(
                        "Content",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 40.sp
                    )
                }
            }
        )
    }
}

@ComponentPreview
@Composable
private fun ScreenContentWrapperErrorPreview() {
    PreviewSurface {
        ScreenContentWrapper(
            state = UIState(
                error = ErrorIndicatorVo(
                    text = StringModel.String("Error message"),
                    buttonVo = ButtonVo(
                        text = StringModel.Resource(id = R.string.try_again),
                        onClick = {}
                    )
                ),
                data = null
            ),
            content = {
                Column {
                    Text(
                        "Content",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 40.sp
                    )
                }
            }
        )
    }
}

