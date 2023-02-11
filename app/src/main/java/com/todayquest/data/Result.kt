package com.todayquest.data

sealed class AsyncResult<out R> {
    data class Success<out T>(val data: T? = null) : AsyncResult<T>()
    data class Error(val message: String) : AsyncResult<Nothing>()

    fun handleResult(onSuccess: () -> Unit, onError: (String) -> Unit) {
        when(this) {
            is Success -> {
                onSuccess()
            }
            is Error -> {
                onError(message)
            }
        }
    }
}

