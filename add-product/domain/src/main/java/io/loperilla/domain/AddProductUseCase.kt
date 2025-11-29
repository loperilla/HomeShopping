package io.loperilla.domain

import io.loperilla.domain.repository.ProductsRepository

/*****
 * Project: HomeShopping
 * From: io.loperilla.domain
 * Created By Manuel Lopera on 4/10/25 at 15:44
 * All rights reserved 2025
 */
class AddProductUseCase(
    private val productRepository: ProductsRepository
) {
    suspend operator fun invoke(name: String) = productRepository.addProduct(name)
}