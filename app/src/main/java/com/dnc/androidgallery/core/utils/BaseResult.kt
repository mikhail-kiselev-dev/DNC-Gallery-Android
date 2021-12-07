@file:Suppress("unused")
package com.dnc.androidgallery.core.utils

sealed class BaseResult<out T : Any> {

    data class Success<out T : Any>(val data: T) : BaseResult<T>()

    data class Error(val exception: Throwable) : BaseResult<Nothing>()

    companion object {
        private val unitSingleton = Success(Unit)
        fun getUnit(): Success<Unit> = unitSingleton
    }
}

inline fun <T, R : Any> T.wrapResult(block: T.() -> R): BaseResult<R> {
    return runCatching(block).fold(
        onSuccess = { BaseResult.Success(it) },
        onFailure = { BaseResult.Error(it) }
    )
}

inline fun <T, R : Any> T.wrapResultFlatten(block: T.() -> BaseResult<R>): BaseResult<R> {
    return runCatching(block).fold(
        onSuccess = { it },
        onFailure = { BaseResult.Error(it) }
    )
}

inline fun BaseResult<Unit>.onSuccessValue(block: () -> Unit): BaseResult<Unit> {
    if (this is BaseResult.Success) {
        block()
    }

    return this
}

inline fun <T : Any> BaseResult<T>.onSuccessValue(block: (T) -> Unit): BaseResult<T> {
    if (this is BaseResult.Success) {
        block(data)
    }

    return this
}

fun <T : Any> BaseResult<T>.getOrNull(): T? {
    return if (this is BaseResult.Success) {
        this.data
    } else {
        if (this is BaseResult.Error) loge("Couldn't get data from BaseResult, exception: $exception")
        null
    }
}

inline fun <T : Any> BaseResult<T>.onErrorValue(block: (Throwable) -> Unit): BaseResult<T> {
    if (this is BaseResult.Error) {
        block(exception)
    }

    return this
}

inline fun <T : Any, R : Any> BaseResult<T>.map(block: (T) -> R): BaseResult<R> {
    return when (this) {
        is BaseResult.Success -> BaseResult.Success(block(data))
        is BaseResult.Error -> this
    }
}

inline fun <T : Any, R : Any> BaseResult<T>.flatMap(block: (T) -> BaseResult<R>): BaseResult<R> {
    return when (this) {
        is BaseResult.Success -> block(data)
        is BaseResult.Error -> this
    }
}

inline fun <T : Any, R : Any> BaseResult<List<T>>.listMap(block: (T) -> R): BaseResult<List<R>> {
    return map { it.map(block) }
}

inline fun <T : Any, R : Any> BaseResult<List<T>>.listMapNotNull(block: (T) -> R?): BaseResult<List<R>> {
    return map { it.mapNotNull(block) }
}

inline fun <T : Any> BaseResult<T>.onErrorReturn(block: (Throwable) -> BaseResult<T>): BaseResult<T> {
    if (this is BaseResult.Error) {
        return block(exception)
    }

    return this
}

inline fun <T : Any> BaseResult<T>.dispatchOrThrow(
    onSuccessValue: (() -> T) = { (this as BaseResult.Success).data }
): T =
    when (this) {
        is BaseResult.Success -> {
            onSuccessValue.invoke()
        }
        is BaseResult.Error -> {
            throw this.exception
        }
    }
