package io.loperilla.datasource.firebase.database

import com.google.firebase.firestore.FirebaseFirestoreException
import io.loperilla.datasource.firebase.reference.CustomReference
import io.loperilla.model.database.Commerce
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
 * Created By Manuel Lopera on 24/9/23 at 20:39
 * All rights reserved 2023
 */
class CommerceDatabase @Inject constructor(
    private val commerceReference: CustomReference.COMMERCE_REFERENCE
) {
    fun getAllCommerces(): Flow<ReadDatabaseResult<List<Commerce>>> = callbackFlow {
        with(commerceReference.commerceReference) {
            val subscription = this.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    error.printStackTrace()
                    trySend(ReadDatabaseResult.FAIL(error.message ?: "lista vacía"))
                    close(error)
                    return@addSnapshotListener
                }
                snapshot?.let { querySnapshot ->
                    val shoppingItemList = mutableListOf<Commerce>()
                    querySnapshot.documents.forEach { documentSnapshot ->
                        Timber.tag("Commerce").i("$documentSnapshot")
                        val item = documentSnapshot.toObject(Commerce::class.java)
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

    suspend fun addCommerce(item: Commerce): PostDatabaseResult =
        withContext(Dispatchers.IO) {
            Timber.i("addCommerce $item")
            val deferred = CompletableDeferred<PostDatabaseResult>(PostDatabaseResult.SUCCESS)
            with(commerceReference) {
                try {
                    commerceReference.add(item).await()
                    deferred.complete(PostDatabaseResult.SUCCESS)
                } catch (ex: FirebaseFirestoreException) {
                    ex.printStackTrace()
                    deferred.complete(PostDatabaseResult.FAIL(ex))
                }
            }
            deferred.await()
        }

}
