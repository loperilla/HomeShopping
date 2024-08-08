package io.loperilla.onboarding_domain.model.result

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding_domain.model.result
 * Created By Manuel Lopera on 8/8/24 at 16:38
 * All rights reserved 2024
 */
sealed class DomainResult<T> {
    data class Success<T>(val data: T) : DomainResult<T>()
    data class Error<T>(val error: DomainError) : DomainResult<T>()
}