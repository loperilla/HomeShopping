package io.loperilla.onboarding_domain.repository

import io.loperilla.onboarding_domain.model.database.product.Product
import io.loperilla.onboarding_domain.model.database.product.ProductDto
import kotlinx.coroutines.flow.Flow

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding_domain.repository
 * Created By Manuel Lopera on 25/8/24 at 19:23
 * All rights reserved 2024
 */
interface ProductRepository {
    suspend fun getProductsByCommerce(commerceKey: String): Flow<List<Product>>
    suspend fun addProduct(dtoProduct: ProductDto, bytes: ByteArray?): Result<Unit>
}