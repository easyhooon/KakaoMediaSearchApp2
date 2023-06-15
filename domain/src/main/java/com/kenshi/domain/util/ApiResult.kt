package com.kenshi.domain.util

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T): ApiResult<T>()
    data class Error(val code: Int, val message: String?): ApiResult<Nothing>()
    // object Loading: ApiResult<Nothing>()
    data class Exception(val exception: Throwable): ApiResult<Nothing>()
}
