package io.loperilla.data.repository

import io.loperilla.datasource.database.dao.QueryDao
import io.loperilla.datasource.database.entities.QueryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/*****
 * Project: HomeShopping
 * From: io.loperilla.data.repository
 * Created By Manuel Lopera on 26/8/23 at 19:35
 * All rights reserved 2023
 */
class QueryRepository @Inject constructor(
    private val queryDao: QueryDao
) {
    fun insertNewQuery(query: String) =
        queryDao.insertNewQuery(QueryEntity(query = query))

    fun getQueries(): Flow<List<String>> = queryDao.getPreviousQuery().map { list ->
        list.map { it.query }
    }

    fun removeQuery(queryToDelete: String) {
        queryDao.removeQuery(QueryEntity(queryToDelete))
    }
}