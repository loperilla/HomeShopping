package io.loperilla.domain.repository

import io.loperilla.domain.model.DomainResult
import io.loperilla.domain.model.product.Product

/*****
 * Project: HomeShopping
 * From: io.loperilla.domain.repository
 * Created By Manuel Lopera on 4/10/25 at 12:11
 * All rights reserved 2025
 */
interface ProductsRepository {
    suspend fun getAllProducts(): DomainResult<List<Product>>
    suspend fun removeProduct(id: String): DomainResult<Unit>
    suspend fun addProduct(name: String): DomainResult<Unit>
}