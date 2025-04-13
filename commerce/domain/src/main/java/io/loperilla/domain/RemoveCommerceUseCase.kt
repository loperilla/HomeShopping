package io.loperilla.domain

import io.loperilla.domain.repository.CommerceRepository

/*****
 * Project: HomeShopping
 * From: io.loperilla.domain
 * Created By Manuel Lopera on 13/4/25 at 11:31
 * All rights reserved 2025
 */
class RemoveCommerceUseCase(
    private val commerceRepository: CommerceRepository
) {
    suspend operator fun invoke(id: String) = commerceRepository.removeCommerce(id)
}