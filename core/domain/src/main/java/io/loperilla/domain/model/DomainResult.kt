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
    val message: String? = null
) {
    data class EmailAlreadyInUse(val throwable: Throwable? = null) :
        DomainError(throwable?.message ?: "")

    data class UnknownError(val throwable: Throwable? = null) :
        DomainError(throwable?.message ?: "")

    data class CreateCredentialsError(val throwable: Throwable? = null) :
        DomainError(throwable?.message ?: "")

    data class CredentialCancellationError(val throwable: Throwable? = null) :
        DomainError(throwable?.message ?: "")

    data class NoCredentialError(val throwable: Throwable? = null) :
        DomainError(throwable?.message ?: "")

    data class GetCredentialError(val throwable: Throwable? = null) :
        DomainError(throwable?.message ?: "")

    data object EmptyUser : DomainError()
    data object EmptyCredential : DomainError()
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
