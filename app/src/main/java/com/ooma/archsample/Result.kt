package com.ooma.archsample

sealed class Result<out T> {
    object Loading : Result<Any>()
    data class Failure(val throwable: Throwable) : Result<Any>()
    data class Success<out T>(val data: T) : Result<T>()
}