package io.loperilla.datasource.database.dao.fake

import io.loperilla.datasource.database.dao.QueryDao
import io.loperilla.datasource.database.entities.QueryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/*****
 * Project: HomeShopping
 * From: io.loperilla.datasource.database.dao.fake
 * Created By Manuel Lopera on 4/9/23 at 20:27
 * All rights reserved 2023
 */
class QueryDaoFake : QueryDao {
    private val queryList = mutableListOf<QueryEntity>()
    override fun insertNewQuery(query: QueryEntity) {
        if (!queryList.contains(query)) {
            queryList.add(query)
        }
    }

    override fun getPreviousQuery(): Flow<List<QueryEntity>> = flow {
        emit(queryList)
    }

    override fun getQueriesBy(inputToFilter: String): Flow<List<QueryEntity>> = flow {
        emit(
            queryList.filter {
                it.query.contains(inputToFilter)
            }
        )
    }

    override fun removeQuery(queryToDelete: QueryEntity) {
        queryList.remove(queryToDelete)
    }
}
