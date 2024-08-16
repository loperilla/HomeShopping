package io.loperilla.datasource.firebase.database

import javax.inject.Inject

/*****
 * Project: HomeShopping
 * From: io.loperilla.datasource.firebase.database
 * Created By Manuel Lopera on 25/8/23 at 14:40
 * All rights reserved 2023
 */
class ShoppingCartListFirebaseDatabase @Inject constructor(
//    private val dataStore: UserDataStore,
//    private val shoppingListReference: SHOPPING_LIST_REFERENCE
) {
//    fun getAllShoppingBuy(): Flow<io.loperilla.onboarding_domain.model.database.result.ReadDatabaseResult<List<io.loperilla.onboarding_domain.model.database.ShoppingItem>>> {
//        val userId = dataStore.getString(UID_PREFERENCE)
//        return callbackFlow {
//            if (userId.isEmpty()) {
//                trySend(io.loperilla.onboarding_domain.model.database.result.ReadDatabaseResult.FAIL("No está loggeado"))
//                close()
//                return@callbackFlow
//            }
//            val listener = shoppingListReference.shoppingReference.addValueEventListener(
//                object : ValueEventListener {
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        val snapShoppingList = snapshot.children.mapNotNull { dataSnapshot ->
//                            val shoppingItem = dataSnapshot.getValue(io.loperilla.onboarding_domain.model.database.ShoppingItem::class.java)
//                            dataSnapshot.key?.let {
//                                shoppingItem?.copy(key = it)
//                            }
//                        }
//                        trySend(io.loperilla.onboarding_domain.model.database.result.ReadDatabaseResult.SUCCESS(snapShoppingList))
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//                        close(error.toException())
//                    }
//                }
//            )
//            awaitClose { shoppingListReference.shoppingReference.removeEventListener(listener) }
//        }.flowOn(Dispatchers.IO)
//    }
}
