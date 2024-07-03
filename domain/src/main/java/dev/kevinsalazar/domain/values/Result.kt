package dev.kevinsalazar.domain.values


import dev.kevinsalazar.domain.errors.Error as RootError

@Suppress("UNCHECKED_CAST")
sealed interface Result<out D, out E : RootError> {
    data class Success<out D, out E : RootError>(val data: D) : Result<D, E>
    data class Error<out D, out E : RootError>(val error: E) : Result<D, E>

    val isSuccess: Boolean get() = this is Success
    val isError: Boolean get() = this is Error

    fun get(): D = this as D
    fun error(): E = this as E

    fun getOrNull(): D? = this as? D
    fun errorOrNull(): E? = this as? E
}

inline fun <D, E : RootError> Result<D, E>.onFailure(
    action: (exception: RootError) -> Unit
): Result<D, E> {
    if (this is Result.Error) action.invoke(error)
    return this
}

inline fun <D, E : RootError> Result<D, E>.onSuccess(action: (value: D) -> Unit): Result<D, E> {
    if (this is Result.Success) action(data)
    return this
}
