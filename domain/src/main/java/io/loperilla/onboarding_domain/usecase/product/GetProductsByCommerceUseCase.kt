package io.loperilla.onboarding_domain.usecase.product

import io.loperilla.onboarding_domain.repository.ProductRepository
import javax.inject.Inject

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding_domain.usecase.product
 * Created By Manuel Lopera on 28/8/24 at 14:08
 * All rights reserved 2024
 */
class GetProductsByCommerceUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(commerceId: String) = productRepository.getProductsByCommerce(commerceId)
}
