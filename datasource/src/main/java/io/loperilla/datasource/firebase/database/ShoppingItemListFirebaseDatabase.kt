package io.loperilla.datasource.firebase.database

import com.google.firebase.firestore.FirebaseFirestoreException
import io.loperilla.datasource.firebase.reference.CustomReference
import io.loperilla.model.database.ShoppingItem
import io.loperilla.model.database.result.PostDatabaseResult
import io.loperilla.model.database.result.ReadDatabaseResult
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

/*****
 * Project: HomeShopping
 * From: io.loperilla.datasource.firebase.database
 * Created By Manuel Lopera on 30/8/23 at 20:39
 * All rights reserved 2023
 */
class ShoppingItemListFirebaseDatabase @Inject constructor(
    private val shoppingItemListReference: CustomReference.SHOPPING_ITEM_LIST_COLLECTION
) {
    fun getAllItems(): Flow<ReadDatabaseResult<List<ShoppingItem>>> = callbackFlow {
        Timber.i("getAllItems")
        with(shoppingItemListReference.itemListCollection) {
            Timber.tag("getAllItems collection").i("$this")
            val subscription = this.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    error.printStackTrace()
                    trySend(ReadDatabaseResult.FAIL(error.message ?: "lista vacía"))
                    close(error)
                    return@addSnapshotListener
                }
                snapshot?.let { querySnapshot ->
                    Timber.tag("getAllItems querySnapshot").i("$querySnapshot")
                    Timber.tag("getAllItems querySnapshot").i("$querySnapshot")
                    val shoppingItemList = mutableListOf<ShoppingItem>()
                    querySnapshot.documents.forEach { documentSnapshot ->
                        Timber.tag("getAllItems documentSnapshot").i("$documentSnapshot")
                        val item = documentSnapshot.toObject(ShoppingItem::class.java)
                        item?.let {
                            it.key = documentSnapshot.id
                            shoppingItemList.add(it)
                        }
                    }
                    trySend(ReadDatabaseResult.SUCCESS(shoppingItemList.toList())).isSuccess
                }
            }
            awaitClose { subscription.remove() }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun addItemShopping(item: ShoppingItem, bitmapToUpload: ByteArray): PostDatabaseResult =
        withContext(Dispatchers.IO) {
            Timber.i("addItemShopping $item")
            val deferred = CompletableDeferred<PostDatabaseResult>(PostDatabaseResult.SUCCESS)

            with(shoppingItemListReference) {
                try {
                    val imageRef = imageStorageReference.child(item.imageRef())
                    imageRef.putBytes(bitmapToUpload).await()
                    val url = imageRef.downloadUrl.await()
                    val itemToUpload = item.copy(
                        imageUrl = url.toString()
                    )
                    itemListCollection.add(itemToUpload).await()
                    deferred.complete(PostDatabaseResult.SUCCESS)
                } catch (ex: FirebaseFirestoreException) {
                ex.printStackTrace()
                deferred.complete(PostDatabaseResult.FAIL(ex))
            }
        }

            deferred.await()
    }
}