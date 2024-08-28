package io.loperilla.data.impl

import io.loperilla.onboarding_domain.model.database.product.ProductDto
import io.loperilla.onboarding_domain.repository.ProductRepository
import javax.inject.Inject

/*****
 * Project: HomeShopping
 * From: io.loperilla.data.impl
 * Created By Manuel Lopera on 26/8/24 at 18:50
 * All rights reserved 2024
 */
class ProductsRepositoryImpl @Inject constructor(

): ProductRepository {

    override suspend fun getProductsByCommerce(commerceKey: String) = TODO()
    override suspend fun addProduct(dtoProductDto: ProductDto): Result<Unit> = TODO()
}
