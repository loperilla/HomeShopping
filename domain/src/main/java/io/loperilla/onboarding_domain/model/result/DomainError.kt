package io.loperilla.onboarding_domain.model.result

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding_domain.model.result
 * Created By Manuel Lopera on 8/8/24 at 16:39
 * All rights reserved 2024
 */
sealed class DomainError {
    data class Unknown(val exception: Throwable?) : DomainError()
}