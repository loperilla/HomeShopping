package io.loperilla.data.impl

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.storage.StorageReference
import io.loperilla.onboarding_domain.model.database.product.ProductDto
import io.loperilla.onboarding_domain.repository.ProductRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/*****
 * Project: HomeShopping
 * From: io.loperilla.data.impl
 * Created By Manuel Lopera on 26/8/24 at 18:50
 * All rights reserved 2024
 */
class ProductsRepositoryImpl @Inject constructor(
    private val storage: StorageReference,
    private val productCollection: CollectionReference
): ProductRepository {

    override suspend fun getProductsByCommerce(commerceKey: String) = TODO()
    override suspend fun addProduct(dtoProduct: ProductDto, bytes: ByteArray?): Result<Unit> {
        return try {
            var productToCreate = dtoProduct
            bytes?.let {
                val imageRef = storage.child(dtoProduct.buildImageName())
                imageRef.putBytes(it).await()
                val url = imageRef.downloadUrl.await()
                productToCreate = productToCreate.copy(
                    urlImage = url.toString()
                )
            }
            productCollection.add(productToCreate).await()
            Result.success(Unit)
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }
}
