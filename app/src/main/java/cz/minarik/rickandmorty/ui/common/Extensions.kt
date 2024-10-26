package cz.minarik.rickandmorty.ui.common

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
