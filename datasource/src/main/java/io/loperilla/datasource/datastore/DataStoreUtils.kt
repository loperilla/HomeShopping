package io.loperilla.datasource.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

/*****
 * Project: CompraCasa
 * From: io.loperilla.data.datastore
 * Created By Manuel Lopera on 21/4/23 at 13:56
 * All rights reserved 2023
 */
object DataStoreUtils {
    private const val DSNAME = "DATASTORE"
    val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(
        name = DSNAME
    )

    object Constants {
        const val UID_PREFERENCE = "USER_UID"
    }
}