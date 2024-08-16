package io.loperilla.datasource.firebase.database

import javax.inject.Inject

/*****
 * Project: HomeShopping
 * From: io.loperilla.datasource.firebase.database
 * Created By Manuel Lopera on 30/8/23 at 20:39
 * All rights reserved 2023
 */
class ShoppingItemListFirebaseDatabase @Inject constructor(
//    private val shoppingItemListReference: CustomReference.SHOPPING_ITEM_LIST_COLLECTION
) {
//    fun getAllItems(): Flow<io.loperilla.onboarding_domain.model.database.result.ReadDatabaseResult<List<io.loperilla.onboarding_domain.model.database.ShoppingItem>>> = callbackFlow {
//        Timber.i("getAllItems")
//        with(shoppingItemListReference.itemListCollection) {
//            Timber.tag("getAllItems collection").i("$this")
//            val subscription = this.addSnapshotListener { snapshot, error ->
//                if (error != null) {
//                    error.printStackTrace()
//                    trySend(io.loperilla.onboarding_domain.model.database.result.ReadDatabaseResult.FAIL(error.message ?: "lista vacía"))
//                    close(error)
//                    return@addSnapshotListener
//                }
//                snapshot?.let { querySnapshot ->
//                    Timber.tag("getAllItems querySnapshot").i("$querySnapshot")
//                    Timber.tag("getAllItems querySnapshot").i("$querySnapshot")
//                    val shoppingItemList = mutableListOf<io.loperilla.onboarding_domain.model.database.ShoppingItem>()
//                    querySnapshot.documents.forEach { documentSnapshot ->
//                        Timber.tag("getAllItems documentSnapshot").i("$documentSnapshot")
//                        val item = documentSnapshot.toObject(io.loperilla.onboarding_domain.model.database.ShoppingItem::class.java)
//                        item?.let {
//                            it.key = documentSnapshot.id
//                            shoppingItemList.add(it)
//                        }
//                    }
//                    trySend(io.loperilla.onboarding_domain.model.database.result.ReadDatabaseResult.SUCCESS(shoppingItemList.toList())).isSuccess
//                }
//            }
//            awaitClose { subscription.remove() }
//        }
//    }.flowOn(Dispatchers.IO)
//
//    suspend fun addItemShopping(item: io.loperilla.onboarding_domain.model.database.ShoppingItem, bitmapToUpload: ByteArray): io.loperilla.onboarding_domain.model.database.result.PostDatabaseResult =
//        withContext(Dispatchers.IO) {
//            Timber.i("addItemShopping $item")
//            val deferred = CompletableDeferred<io.loperilla.onboarding_domain.model.database.result.PostDatabaseResult>(
//                io.loperilla.onboarding_domain.model.database.result.PostDatabaseResult.SUCCESS)
//
//            with(shoppingItemListReference) {
//                try {
//                    val imageRef = imageStorageReference.child(item.imageRef())
//                    imageRef.putBytes(bitmapToUpload).await()
//                    val url = imageRef.downloadUrl.await()
//                    val itemToUpload = item.copy(
//                        imageUrl = url.toString()
//                    )
//                    itemListCollection.add(itemToUpload).await()
//                    deferred.complete(io.loperilla.onboarding_domain.model.database.result.PostDatabaseResult.SUCCESS)
//                } catch (ex: FirebaseFirestoreException) {
//                ex.printStackTrace()
//                deferred.complete(io.loperilla.onboarding_domain.model.database.result.PostDatabaseResult.FAIL(ex))
//            }
//        }
//
//            deferred.await()
//    }
}