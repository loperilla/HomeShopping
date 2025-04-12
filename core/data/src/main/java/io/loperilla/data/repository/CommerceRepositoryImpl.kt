package io.loperilla.data.repository

import io.loperilla.data.network.CommerceCollection
import io.loperilla.domain.model.DomainError
import io.loperilla.domain.model.DomainResult
import io.loperilla.domain.model.commerce.Commerce
import io.loperilla.domain.model.commerce.CommerceDto
import io.loperilla.domain.repository.CommerceRepository
import kotlinx.coroutines.tasks.await

/*****
 * Project: HomeShopping
 * From: io.loperilla.data.repository
 * Created By Manuel Lopera on 12/4/25 at 10:38
 * All rights reserved 2025
 */
class CommerceRepositoryImpl(
    private val collection: CommerceCollection
): CommerceRepository {
    override suspend fun getCommerces(): DomainResult<List<Commerce>> {
        return try {
            val querySnapshot = collection.get().await()
            val commerces = querySnapshot.documents.mapNotNull {
                val id = it.id
                it.toObject(Commerce::class.java)?.copy(id = id)
            }
            DomainResult.Success(commerces)
        } catch (ex: Exception) {
            ex.printStackTrace()
            DomainResult.Error(DomainError.UnknownError(ex))
        }
    }

    override suspend fun addCommerce(commerceDto: CommerceDto): DomainResult<Unit> {
        return try {
            collection.add(commerceDto).await()
            DomainResult.Success(Unit)
        } catch (ex: Exception) {
            ex.printStackTrace()
            DomainResult.Error(DomainError.UnknownError(ex))
        }
    }

    override suspend fun removeCommerce(commerceId: String): DomainResult<Unit> {
        return try {
            collection.document(commerceId).delete().await()
            DomainResult.Success(Unit)
        } catch (ex: Exception) {
            ex.printStackTrace()
            DomainResult.Error(DomainError.UnknownError(ex))
        }
    }

    override suspend fun updateCommerce(
        updatedIdCommerce: String,
        commerceDto: CommerceDto
    ): DomainResult<Unit> {
        return try {
            collection.document(updatedIdCommerce).set(commerceDto).await()
            DomainResult.Success(Unit)
        } catch (ex: Exception) {
            ex.printStackTrace()
            DomainResult.Error(DomainError.UnknownError(ex))
        }
    }
}