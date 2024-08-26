package io.loperilla.data.impl

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.snapshots
import io.loperilla.data.model.CommerceModel
import io.loperilla.onboarding_domain.model.database.Commerce
import io.loperilla.onboarding_domain.repository.CommerceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/*****
 * Project: HomeShopping
 * From: io.loperilla.data.firebase.database
 * Created By Manuel Lopera on 11/8/24 at 12:09
 * All rights reserved 2024
 */
class CommerceRepositoryImpl @Inject constructor(
    private val commerceCollection: CollectionReference
) : CommerceRepository {
    override suspend fun getCommerces(): Flow<List<Commerce>> {
        return commerceCollection
            .snapshots()
            .map { snapshots ->
                snapshots.documents.mapNotNull {
                    val id = it.id
                    it.toObject(CommerceModel::class.java)?.copy(id = id)?.toDomain()
                }
            }
    }
}