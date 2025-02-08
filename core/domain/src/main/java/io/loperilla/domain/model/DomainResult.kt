package io.loperilla.domain.model

/*****
 * Project: HomeShopping
 * From: io.loperilla.domain.model
 * Created By Manuel Lopera on 2/2/25 at 18:43
 * All rights reserved 2025
 */
sealed class DomainResult<T> {
    data class Success<T>(val data: T) : DomainResult<T>()
    data class Error<T>(val error: DomainError) : DomainResult<T>()
}

sealed class DomainError(
    val code: Int? = null,
    val message: String? = null
) {
    data class NetworkError(val apiCodeError: Int, val apiErrorMessage: String?) :
        DomainError(apiCodeError, apiErrorMessage)

    data class UnknownError(val throwable: Throwable? = null) :
        DomainError(null, throwable?.message ?: "")
}

val <T> DomainResult<T>.isSuccess: Boolean
    get() = this is DomainResult.Success<T>

fun <T> DomainResult<T>.fold(
    onSuccess: (T) -> Unit,
    onError: (DomainError) -> Unit
): Unit = when (this) {
    is DomainResult.Success -> onSuccess(data)
    is DomainResult.Error -> onError(error)
}

fun <T> DomainResult<T>.getOrNull(): T? = when (this) {
    is DomainResult.Success -> data
    is DomainResult.Error -> null
}
