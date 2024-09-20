package cz.minarik.rickandmorty.ui.screens.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import cz.minarik.rickandmorty.R
import cz.minarik.rickandmorty.ui.core.composable.ComponentPreview
import cz.minarik.rickandmorty.ui.core.composable.ErrorIndicator
import cz.minarik.rickandmorty.ui.core.composable.PreviewSurface
import cz.minarik.rickandmorty.ui.core.model.ButtonVo
import cz.minarik.rickandmorty.ui.core.model.ErrorIndicatorVo
import cz.minarik.rickandmorty.ui.core.model.StringModel
import cz.minarik.rickandmorty.ui.theme.SpacingLarge

/**
 * State footer based on [LoadState]. Typically used to show progressBar or error while loading next
 * page.
 *
 * @param loadState Given state.
 * @param onTryAgain Callback for retry button.
 */
@Composable
fun LoadStateFooter(
    loadState: LoadState,
    onTryAgain: () -> Unit
) {
    when (loadState) {
        is LoadState.Loading -> {
            // next page loading
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpacingLarge)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = MaterialTheme.colors.onBackground,
                )
            }
        }

        is LoadState.Error -> {
            // next page error
            ErrorIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpacingLarge),
                errorIndicatorVo = ErrorIndicatorVo(
                    text = StringModel.String(loadState.error.message ?: ""),
                    buttonVo = ButtonVo(
                        text = StringModel.Resource(id = R.string.try_again),
                        onClick = onTryAgain
                    ),
                    showOverlay = false,
                )
            )
        }

        is LoadState.NotLoading -> Unit
    }
}

@ComponentPreview
@Composable
private fun LoadStateFooterLoadingPreview() {
    PreviewSurface {
        LoadStateFooter(
            loadState = LoadState.Loading,
            onTryAgain = {}
        )
    }
}

@ComponentPreview
@Composable
private fun LoadStateFooterErrorPreview() {
    PreviewSurface {
        LoadStateFooter(
            loadState = LoadState.Error(Exception("Error")),
            onTryAgain = {}
        )
    }
}
