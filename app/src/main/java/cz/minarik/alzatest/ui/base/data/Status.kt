package cz.minarik.alzatest.ui.base.data

import androidx.compose.runtime.Immutable

typealias Init = Status.Init
typealias Loading = Status.Loading
typealias Success<Type> = Status.Success<Type>
typealias Failure = Status.Failure

sealed interface Status {

    object Init : Status
    object Loading : Status

    @Immutable
    data class Success<out T>(val value: T) : Status

    @Immutable
    data class Failure(val error: Throwable) : Status
}