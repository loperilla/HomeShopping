package io.loperilla.onboarding_domain.usecase.product

import io.loperilla.onboarding_domain.model.database.product.ProductDto
import io.loperilla.onboarding_domain.repository.ProductRepository

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding_domain.usecase.itemShopping
 * Created By Manuel Lopera on 31/8/23 at 16:39
 * All rights reserved 2023
 */
class AddProductUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(
        dtoProduct: ProductDto,
        byteArray: ByteArray?
    ): Result<Unit> = repository.addProduct(dtoProduct, byteArray)
}
