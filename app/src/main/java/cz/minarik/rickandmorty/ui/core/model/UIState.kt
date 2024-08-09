package cz.minarik.rickandmorty.ui.core.model

/**
 * Class holding current state of UI (usually for one or more screens).
 *
 * @param T type of [data]
 * @property data UI related data that are part of UI state
 * @property error contains data about occurred error, null when there is no error
 * @property loading contains data about loading, null if there is no loading for the UIState
 */
open class UIState<T>(
    val data: T,
    val error: ErrorModel? = null,
    val loading: Boolean = false,
)
