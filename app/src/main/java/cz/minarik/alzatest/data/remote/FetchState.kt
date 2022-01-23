package cz.minarik.alzatest.data.remote

sealed class FetchState<T>
class Loading<T> : FetchState<T>()
data class SuccessWithData<T>(val content: T) : FetchState<T>()
data class FailedWithError<T>(
        val error: String,
) : FetchState<T>()