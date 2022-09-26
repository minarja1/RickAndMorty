package cz.minarik.alzatest.ui.screens.home.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import cz.minarik.alzatest.ui.composables.ErrorView

/**
 * State screen based on [LoadState]
 *
 * @param loadState Given state.
 * @param onTryAgain Callback for retry button.
 */
@Composable
fun BoxScope.LoadStateScreen(
    loadState: LoadState,
    onTryAgain: () -> Unit
) {
    loadState.apply {
        when (this) {
            is LoadState.Loading -> {
                // initial loading
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is LoadState.Error -> {
                // initial error
                ErrorView(
                    modifier = Modifier.fillMaxSize(),
                    error = this.error.message ?: "",
                    onTryAgainClicked = onTryAgain
                )
            }
            is LoadState.NotLoading -> Unit /* handled in list */
        }
    }
}
