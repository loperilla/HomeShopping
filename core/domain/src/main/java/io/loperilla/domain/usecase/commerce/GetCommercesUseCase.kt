package io.loperilla.domain.usecase.commerce

import io.loperilla.domain.repository.CommerceRepository

/*****
 * Project: HomeShopping
 * From: io.loperilla.domain
 * Created By Manuel Lopera on 12/4/25 at 10:33
 * All rights reserved 2025
 */
class GetCommercesUseCase (
    private val repository: CommerceRepository
) {
    suspend operator fun invoke() = repository.getCommerces()
}