package cz.minarik.rickandmorty.common.base

/**
 * Base class for all states of fetching data.
 */
sealed class FetchState<out T>

/**
 * Loading state.
 */
data object Loading : FetchState<Nothing>()

/**
 * Success state with data.
 *
 * @param content data
 */
data class SuccessWithData<T>(
    val content: T
) : FetchState<T>()

/**
 * Failed state with error.
 *
 * @param error error message
 */
data class FailedWithError(
    val error: Throwable,
) : FetchState<Nothing>()
