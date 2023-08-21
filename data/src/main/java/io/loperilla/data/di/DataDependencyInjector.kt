package io.loperilla.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.loperilla.data.datastore.DataStoreRepository
import io.loperilla.data.firebase.auth.FirebaseAuthRepository
import io.loperilla.datasource.datastore.UserDataStoreDataSourceImpl
import io.loperilla.datasource.firebase.auth.FirebaseAuthDataSourceImpl

/*****
 * Project: CompraCasa
 * From: io.loperilla.data.di
 * Created By Manuel Lopera on 23/4/23 at 18:02
 * All rights reserved 2023
 */
@Module
@InstallIn(SingletonComponent::class)
object DataDependencyInjector {
    @Provides
    fun providesDataStoreRepository(
        userPref: UserDataStoreDataSourceImpl
    ): DataStoreRepository = DataStoreRepository(userPref)

    @Provides
    fun provideFirebaseAuth(
        firebaseAuth: FirebaseAuthDataSourceImpl
    ): FirebaseAuthRepository = FirebaseAuthRepository(firebaseAuth)
}
