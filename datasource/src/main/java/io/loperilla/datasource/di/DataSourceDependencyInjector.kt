package io.loperilla.datasource.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.loperilla.datasource.datastore.UserDataStore
import io.loperilla.datasource.datastore.UserDataStoreImpl
import javax.inject.Singleton

/*****
 * Project: CompraCasa
 * From: io.loperilla.datasource.di
 * Created By Manuel Lopera on 23/4/23 at 14:03
 * All rights reserved 2023
 */

@Module
@InstallIn(SingletonComponent::class)
object DataSourceDependencyInjector {

    @Singleton
    @Provides
    fun providesPreferenceDataStore(
        @ApplicationContext context: Context
    ): UserDataStore = UserDataStoreImpl(context)
}
