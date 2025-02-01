package io.loperilla.onboarding_domain.repository

/*****
 * Project: CompraCasa
 * From: io.loperilla.data.datastore
 * Created By Manuel Lopera on 21/4/23 at 13:30
 * All rights reserved 2023
 */

interface UserDataStore {
    suspend fun insertString(key: DataStoreKey, value: String): Result<Unit>
    suspend fun getString(key: DataStoreKey): Result<String>
    suspend fun clearPreferences(): Result<Unit>
}

enum class DataStoreKey {
    UID
}
