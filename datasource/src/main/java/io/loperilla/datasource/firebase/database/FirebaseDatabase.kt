package io.loperilla.datasource.firebase.database

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import io.loperilla.datasource.datastore.DataStoreUtils.Constants.UID_PREFERENCE
import io.loperilla.datasource.datastore.IUserDataStoreDataSource
import io.loperilla.datasource.firebase.reference.CustomReference.SHOPPING_LIST_REFERENCE
import io.loperilla.model.database.DatabaseResult
import io.loperilla.model.database.ShoppingItem
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

/*****
 * Project: HomeShopping
 * From: io.loperilla.datasource.firebase.database
 * Created By Manuel Lopera on 25/8/23 at 14:40
 * All rights reserved 2023
 */
class FirebaseDatabase @Inject constructor(
    private val dataStore: IUserDataStoreDataSource,
    private val shoppingListReference: SHOPPING_LIST_REFERENCE
) {
    fun getAllShoppingBuy(): Flow<DatabaseResult<List<ShoppingItem>>> {
        val userId = dataStore.getString(UID_PREFERENCE)
        val callbackFlow = callbackFlow {
            if (userId.isEmpty()) {
                trySend(DatabaseResult.FAIL("No está loggeado"))
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
                        trySend(DatabaseResult.SUCCESS(snapShoppingList))
                    }

                    override fun onCancelled(error: DatabaseError) {
                        error.toException().printStackTrace()
                        close(error.toException())
                    }
                }
            )
            awaitClose { shoppingListReference.shoppingReference.removeEventListener(listener) }
        }
        return callbackFlow

    }
}
