package io.loperilla.data.repository

import com.google.firebase.firestore.CollectionReference
import io.loperilla.data.network.model.ProductModel
import io.loperilla.domain.model.DomainError
import io.loperilla.domain.model.DomainResult
import io.loperilla.domain.model.product.Product
import io.loperilla.domain.model.product.ProductDto
import io.loperilla.domain.repository.ProductsRepository
import kotlinx.coroutines.tasks.await

/*****
 * Project: HomeShopping
 * From: io.loperilla.data.repository
 * Created By Manuel Lopera on 4/10/25 at 12:12
 * All rights reserved 2025
 */
class ProductsRepositoryImpl(
    private val productCollection: CollectionReference
): ProductsRepository {
    override suspend fun getAllProducts(): DomainResult<List<Product>> {
        return try {
            val querySnapshot = productCollection.get().await()
            val productList = querySnapshot.documents.mapNotNull {
                val id = it.id
                it.toObject(ProductModel::class.java)?.copy(id = id)?.toDomain()
            }
            DomainResult.Success(productList)
        } catch (ex: Exception) {
            ex.printStackTrace()
            DomainResult.Error(DomainError.UnknownError(ex))
        }
    }

    override suspend fun removeProduct(id: String): DomainResult<Unit> {
        return try {
            productCollection.document(id).delete().await()
            DomainResult.Success(Unit)
        } catch (ex: Exception) {
            ex.printStackTrace()
            DomainResult.Error(DomainError.UnknownError(ex))
        }
    }

    override suspend fun addProduct(name: String): DomainResult<Unit> {
        return try {
            productCollection.add(ProductDto(name)).await()
            DomainResult.Success(Unit)
        } catch (ex: Exception) {
            ex.printStackTrace()
            DomainResult.Error(DomainError.UnknownError(ex))
        }
    }
}