package io.loperilla.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import io.loperilla.onboarding_domain.repository.DataStoreKey
import io.loperilla.onboarding_domain.repository.UserDataStore
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
    override suspend fun insertString(key: DataStoreKey, value: String): Result<Unit> {
        return try {
            val keyPreference = findKeyByEnum(key)
            context.userDataStore.edit { preferences ->
                preferences[keyPreference] = value
            }
            Result.success(Unit)
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    override suspend fun getString(key: DataStoreKey): Result<String> {
        return try {
            val keyPreference = findKeyByEnum(key)
            val valueFind = context.userDataStore.data.first()[keyPreference]
            if (valueFind != null) {
                Result.success(valueFind)
            }
            Result.success(context.userDataStore.data.first()[keyPreference] ?: "")
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

    private fun findKeyByEnum(keyToSave: DataStoreKey): Preferences.Key<String> {
        return when(keyToSave) {
            DataStoreKey.UID -> stringPreferencesKey("uid")
        }
    }
}
