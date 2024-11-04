package cz.minarik.rickandmorty.ui.common

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cz.minarik.rickandmorty.data.remote.exception.NoConnectionException
import cz.minarik.rickandmorty.ui.core.model.ErrorIndicatorVo
import cz.minarik.rickandmorty.ui.core.model.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

/**
 * Updates UIState with new data. Keeps loading and error states.
 *
 * This uses the [update] method which handles concurrency.
 *
 * @param T UIState date type
 * @param getNewData function to get new data
 */
fun <T> MutableStateFlow<UIState<T>>.updateData(getNewData: (T) -> T) {
    update { currentState ->
        UIState(
            data = getNewData(currentState.data),
            loading = currentState.loading,
            error = currentState.error
        )
    }
}

/**
 * Removes errors and shows loading, keeps data in UIState.
 *
 * @param T UIState date type
 */
fun <T> MutableStateFlow<UIState<T>>.showLoading() {
    update { currentState ->
        UIState(
            data = currentState.data,
            loading = true,
        )
    }
}

/**
 * Removes loading and shows error, keep data in UIState.
 *
 * @param T UIState date type
 */
fun <T> MutableStateFlow<UIState<T>>.showError(error: ErrorIndicatorVo) {
    update { currentState ->
        UIState(
            data = currentState.data,
            error = error,
        )
    }
}

/**
 * Clear all overlays (loading, error). Keep data in UIState.
 *
 * @param T UIState date type
 */
fun <T> MutableStateFlow<UIState<T>>.hideAllOverlays() {
    update { currentState ->
        UIState(
            data = currentState.data
        )
    }
}

/**
 * Converts throwable to display message.
 */
fun Throwable.toDisplayMessage(): String {
    val cause = this.cause
    return when {
        cause is NoConnectionException -> cause.message
        else -> this.message ?: ""
    }
}

/**
 * Returns shared element modifier if sharedTransitionScope and animatedContentScope are not null.
 *
 * @param key key for shared element
 * @param sharedTransitionScope shared transition scope
 * @param animatedContentScope animated content scope
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun getSharedElementModifier(
    key: String,
    sharedTransitionScope: SharedTransitionScope?,
    animatedContentScope: AnimatedContentScope?,
) =
    if (sharedTransitionScope == null || animatedContentScope == null) {
        Modifier
    } else {
        with(sharedTransitionScope) {
            Modifier.sharedBounds(
                rememberSharedContentState(key = key),
                animatedVisibilityScope = animatedContentScope,
                enter = fadeIn(),
                exit = fadeOut(),
                resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds()
            )
        }
    }
