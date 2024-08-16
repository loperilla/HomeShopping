package io.loperilla.datasource.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import io.loperilla.datasource.datastore.DataStoreUtils.userDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/*****
 * Project: CompraCasa
 * From: io.loperilla.data.datastore
 * Created By Manuel Lopera on 21/4/23 at 13:32
 * All rights reserved 2023
 */
class UserDataStoreImpl @Inject constructor(
    private val context: Context
) : UserDataStore {
    private val TAG = UserDataStoreImpl::class.java.simpleName
    override suspend fun insertString(key: Preferences.Key<String>, value: String): Result<Unit> {
        return try {
            context.userDataStore.edit { preferences ->
                preferences[key] = value
            }
            Result.success(Unit)
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    override suspend fun getString(key: Preferences.Key<String>): Result<String> {
        return try {
            val valueFind = context.userDataStore.data.first()[key]
            if (valueFind != null) {
                Result.success(valueFind)
            }
            Result.success(context.userDataStore.data.first()[key] ?: "")
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    override suspend fun clearPreferences(): Result<Unit> {
        return try {
            context.userDataStore.edit { preferences ->
                preferences.clear()
            }
            Result.success(Unit)
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }
}
