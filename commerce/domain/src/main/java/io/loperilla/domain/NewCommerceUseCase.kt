package io.loperilla.domain

import io.loperilla.domain.model.commerce.CommerceDto
import io.loperilla.domain.repository.CommerceRepository

/*****
 * Project: HomeShopping
 * From: io.loperilla.domain
 * Created By Manuel Lopera on 13/4/25 at 10:57
 * All rights reserved 2025
 */
class NewCommerceUseCase(
    private val repository: CommerceRepository
) {
    suspend operator fun invoke(name: String) = repository.addCommerce(CommerceDto(name))
}