package io.loperilla.data.repository

import com.google.firebase.firestore.CollectionReference
import io.loperilla.data.network.model.ShoppingListModel
import io.loperilla.domain.model.DomainError
import io.loperilla.domain.model.DomainResult
import io.loperilla.domain.model.ShoppingList
import io.loperilla.domain.repository.ShoppingListRepository
import kotlinx.coroutines.tasks.await

/*****
 * Project: HomeShopping
 * From: io.loperilla.data.network
 * Created By Manuel Lopera on 21/2/25 at 16:46
 * All rights reserved 2025
 */
class ShoppingListRepositoryImpl(
    private val shoppingListCollection: CollectionReference
): ShoppingListRepository {
    override suspend fun getLastShoppingList(): DomainResult<ShoppingList> {
        return try {
            val querySnapshot = shoppingListCollection.get().await()
            val shoppingList = querySnapshot.documents.mapNotNull {
                val id = it.id
                it.toObject(ShoppingListModel::class.java)?.copy(id = id)?.toDomain()
            }
            DomainResult.Success(shoppingList.first())
        } catch (ex: Exception) {
            ex.printStackTrace()
            DomainResult.Error(DomainError.UnknownError(ex))
        }
    }
}