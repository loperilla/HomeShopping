package io.loperilla.datasource.datastore

import androidx.datastore.preferences.core.Preferences

/*****
 * Project: CompraCasa
 * From: io.loperilla.data.datastore
 * Created By Manuel Lopera on 21/4/23 at 13:30
 * All rights reserved 2023
 */

interface UserDataStore {
    suspend fun insertString(key: Preferences.Key<String>, value: String): Result<Unit>
    suspend fun getString(key: Preferences.Key<String>): Result<String>
    suspend fun clearPreferences(): Result<Unit>
}