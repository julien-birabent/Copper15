package com.example.copper15.data.repository

sealed class ResultState<T> {
    data class Loading<T>(val data: T?) : ResultState<T>()
    data class Success<T>(val data: T) : ResultState<T>()
    data class Error<T>(val throwable: Throwable, val lastData: T?) : ResultState<T>()

    companion object {
        fun <I, O> createFromSameType(resultState: ResultState<I>, data: O): ResultState<O> =
            when (resultState) {
                is Error -> Error(resultState.throwable, data)
                is Loading -> Loading(data)
                is Success -> Success(data)
            }
    }
}

val <T>ResultState<T>.data: T?
    get() = when (this) {
        is ResultState.Loading -> data
        is ResultState.Success -> data
        is ResultState.Error -> lastData
    }