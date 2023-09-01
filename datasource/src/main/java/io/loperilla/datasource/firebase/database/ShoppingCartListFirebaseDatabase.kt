package io.loperilla.datasource.firebase.database

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import io.loperilla.datasource.datastore.DataStoreUtils.Constants.UID_PREFERENCE
import io.loperilla.datasource.datastore.IUserDataStoreDataSource
import io.loperilla.datasource.firebase.reference.CustomReference.SHOPPING_LIST_REFERENCE
import io.loperilla.model.database.ShoppingItem
import io.loperilla.model.database.result.ReadDatabaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/*****
 * Project: HomeShopping
 * From: io.loperilla.datasource.firebase.database
 * Created By Manuel Lopera on 25/8/23 at 14:40
 * All rights reserved 2023
 */
class ShoppingCartListFirebaseDatabase @Inject constructor(
    private val dataStore: IUserDataStoreDataSource,
    private val shoppingListReference: SHOPPING_LIST_REFERENCE
) {
    fun getAllShoppingBuy(): Flow<ReadDatabaseResult<List<ShoppingItem>>> {
        val userId = dataStore.getString(UID_PREFERENCE)
        return callbackFlow {
            if (userId.isEmpty()) {
                trySend(ReadDatabaseResult.FAIL("No está loggeado"))
                close()
                return@callbackFlow
            }
            val listener = shoppingListReference.shoppingReference.addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val snapShoppingList = snapshot.children.mapNotNull { dataSnapshot ->
                            val shoppingItem = dataSnapshot.getValue(ShoppingItem::class.java)
                            dataSnapshot.key?.let {
                                shoppingItem?.copy(key = it)
                            }
                        }
                        trySend(ReadDatabaseResult.SUCCESS(snapShoppingList))
                    }

                    override fun onCancelled(error: DatabaseError) {
                        close(error.toException())
                    }
                }
            )
            awaitClose { shoppingListReference.shoppingReference.removeEventListener(listener) }
        }.flowOn(Dispatchers.IO)
    }
}
