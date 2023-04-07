package cz.minarik.rickandmorty.ui.screens.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import cz.minarik.rickandmorty.ui.composables.ErrorView
import cz.minarik.rickandmorty.ui.dimens.SpacingXSmall

/**
 * State footer based on [LoadState]. Typically used to show progressBar or error while loading next page.
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
                    .padding(SpacingXSmall)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
        is LoadState.Error -> {
            // next page error
            ErrorView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpacingXSmall),
                error = loadState.error.message ?: "",
                onTryAgainClicked = onTryAgain
            )
        }
        is LoadState.NotLoading -> Unit
    }
}
