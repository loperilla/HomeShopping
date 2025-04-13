package io.loperilla.domain.repository

import io.loperilla.domain.model.DomainResult
import io.loperilla.domain.model.commerce.Commerce
import io.loperilla.domain.model.commerce.CommerceDto

/*****
 * Project: HomeShopping
 * From: io.loperilla.domain.repository
 * Created By Manuel Lopera on 12/4/25 at 10:34
 * All rights reserved 2025
 */
interface CommerceRepository {
    suspend fun getCommerces(): DomainResult<List<Commerce>>
    suspend fun addCommerce(commerceDto: CommerceDto): DomainResult<Unit>
    suspend fun removeCommerce(commerceId: String): DomainResult<Unit>
    suspend fun updateCommerce(updatedIdCommerce: String, commerceDto: CommerceDto): DomainResult<Unit>
}