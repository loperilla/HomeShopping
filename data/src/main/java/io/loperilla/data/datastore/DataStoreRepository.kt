package io.loperilla.data.datastore

import io.loperilla.datasource.datastore.IUserDataStoreDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/*****
 * Project: CompraCasa
 * From: io.loperilla.data.datastore
 * Created By Manuel Lopera on 23/4/23 at 14:22
 * All rights reserved 2023
 */
class DataStoreRepository @Inject constructor(
    private val userPreferences: IUserDataStoreDataSource
) {
    fun getString(key: String): Flow<String> =
        userPreferences.getString(key)

    suspend fun insertString(key: String, value: String) =
        userPreferences.insertString(key, value)
}