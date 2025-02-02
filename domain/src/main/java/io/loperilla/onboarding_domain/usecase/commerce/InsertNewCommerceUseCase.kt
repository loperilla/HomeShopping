package io.loperilla.onboarding_domain.usecase.commerce

import io.loperilla.onboarding_domain.repository.CommerceRepository

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding_domain.usecase.commerce
 * Created By Manuel Lopera on 27/8/24 at 13:24
 * All rights reserved 2024
 */
class InsertNewCommerceUseCase(
    private val commerceRepository: CommerceRepository
) {
    suspend operator fun invoke(commerceName: String) = commerceRepository.addCommerce(commerceName)
}