package io.loperilla.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.loperilla.datasource.database.entities.QueryEntity
import kotlinx.coroutines.flow.Flow

/*****
 * Project: HomeShopping
 * From: io.loperilla.datasource.database.dao
 * Created By Manuel Lopera on 26/8/23 at 19:31
 * All rights reserved 2023
 */
@Dao
interface QueryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewQuery(query: QueryEntity)

    @Query("SELECT * FROM QueryEntity")
    fun getPreviousQuery(): Flow<List<QueryEntity>>

    @Query("SELECT * FROM QueryEntity WHERE `query` LIKE '%' || :inputToFilter || '%'")
    fun getQueriesBy(inputToFilter: String): Flow<List<QueryEntity>>
}
