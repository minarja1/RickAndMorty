package cz.minarik.alzatest.data.remote

import cz.minarik.alzatest.data.remote.exception.ApiException

data class ApiResponse<out T>(
    var status: Status,
    val data: T? = null,
    val error: Exception? = null,
) {

    enum class Status {
        ERROR,
        SUCCESS,
    }

    companion object {
        fun <T> success(data: T?): ApiResponse<T> {
            return ApiResponse(
                status = Status.SUCCESS,
                data = data,
            )
        }

        fun <T> error(data: T? = null, message: String? = null): ApiResponse<T> {
            return ApiResponse(
                status = Status.ERROR,
                error = ApiException(message),
                data = data
            )
        }

        fun <T> error(error: Exception? = null): ApiResponse<T> {
            return ApiResponse(
                status = Status.ERROR,
                error = error,
            )
        }

        fun <T> error(error: Throwable? = null): ApiResponse<T> {
            val ex: Exception = Exception(error)
            return ApiResponse(
                status = Status.ERROR,
                error = ex,
            )
        }

        fun <T> error(message: String?): ApiResponse<T> {
            return ApiResponse(
                status = Status.ERROR,
                error = ApiException(message),
            )
        }
    }
}