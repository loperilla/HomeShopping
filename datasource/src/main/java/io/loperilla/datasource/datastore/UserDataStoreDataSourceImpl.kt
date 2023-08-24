package io.loperilla.datasource.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import io.loperilla.datasource.datastore.DataStoreUtils.Constants.UID_PREFERENCE
import io.loperilla.datasource.datastore.DataStoreUtils.userDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

/*****
 * Project: CompraCasa
 * From: io.loperilla.data.datastore
 * Created By Manuel Lopera on 21/4/23 at 13:32
 * All rights reserved 2023
 */
class UserDataStoreDataSourceImpl @Inject constructor(
    private val context: Context
) : IUserDataStoreDataSource {
    private val TAG = UserDataStoreDataSourceImpl::class.java.simpleName

    override fun getAll(): Flow<Preferences> {
        return context.userDataStore.data
    }

    override fun getString(key: String): Flow<String> = flow {
        context.userDataStore.data.map { preferences ->
            preferences[stringPreferencesKey(key)]
        }
    }

    override suspend fun insertString(key: String, value: String) {
        withContext(Dispatchers.IO) {
            context.userDataStore.edit { preferences ->
                preferences[stringPreferencesKey(key)] = value
            }
        }
    }

    override suspend fun clearUid() {
        withContext(Dispatchers.IO) {
            context.userDataStore.edit { preferences ->
                preferences[stringPreferencesKey(UID_PREFERENCE)] = ""
            }
        }
    }
}
