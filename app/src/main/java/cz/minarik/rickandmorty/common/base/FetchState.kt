package cz.minarik.rickandmorty.common.base

/**
 * Base class for all states of fetching data.
 */
sealed class FetchState<T>

/**
 * Loading state.
 */
class Loading<T> : FetchState<T>()

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
data class FailedWithError<T>(
    val error: String,
) : FetchState<T>()
