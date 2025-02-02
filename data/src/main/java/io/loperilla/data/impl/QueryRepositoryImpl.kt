package io.loperilla.data.impl

import io.loperilla.data.database.QueryDao
import io.loperilla.data.database.entities.QueryEntity
import io.loperilla.onboarding_domain.repository.QueryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/*****
 * Project: HomeShopping
 * From: io.loperilla.data.repository
 * Created By Manuel Lopera on 26/8/23 at 19:35
 * All rights reserved 2023
 */
class QueryRepositoryImpl(
    private val queryDao: io.loperilla.data.database.QueryDao
): QueryRepository {
    override fun insertQuery(query: String) =
        queryDao.insertNewQuery(QueryEntity(query = query))

    override fun getQueries(): Flow<List<String>> = queryDao.getPreviousQuery().map { list ->
        list.map { it.query }
    }

    override fun removeQuery(queryToDelete: String) {
        queryDao.removeQuery(QueryEntity(queryToDelete))
    }

    override suspend fun deleteAll() {
        queryDao.deleteAll()
    }
}