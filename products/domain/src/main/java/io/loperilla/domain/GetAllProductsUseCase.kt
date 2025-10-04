package io.loperilla.domain

import io.loperilla.domain.repository.ProductsRepository

/*****
 * Project: HomeShopping
 * From: io.loperilla.domain
 * Created By Manuel Lopera on 4/10/25 at 12:18
 * All rights reserved 2025
 */
class GetAllProductsUseCase(
    private val productsRepository: ProductsRepository
) {
    suspend operator fun invoke() = productsRepository.getAllProducts()
}