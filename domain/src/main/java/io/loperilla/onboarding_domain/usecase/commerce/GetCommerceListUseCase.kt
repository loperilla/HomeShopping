package io.loperilla.onboarding_domain.usecase.commerce

import io.loperilla.onboarding_domain.repository.CommerceRepository

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding_domain.usecase.commerce
 * Created By Manuel Lopera on 11/8/24 at 12:29
 * All rights reserved 2024
 */
class GetCommerceListUseCase(
    private val commerceRepository: CommerceRepository
) {
    suspend operator fun invoke() = commerceRepository.getCommerces()
}