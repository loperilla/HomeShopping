package io.loperilla.data.di

import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.loperilla.data.database.HomeShoppingDatabase
import io.loperilla.data.database.dao.QueryDao
import io.loperilla.data.network.AuthRepositoryImpl
import io.loperilla.domain.repository.AuthRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/*****
 * Project: HomeShopping
 * From: io.loperilla.data.di
 * Created By Manuel Lopera on 2/2/25 at 16:37
 * All rights reserved 2025
 */

val dataModule = module {
    single<HomeShoppingDatabase> {
        Room
            .databaseBuilder(
                androidContext(),
                HomeShoppingDatabase::class.java,
                HomeShoppingDatabase::class.java.simpleName
            ).build()
    }
    factory<QueryDao> { get<HomeShoppingDatabase>().queryDao() }
    single<FirebaseAuth> { Firebase.auth }
    singleOf(::AuthRepositoryImpl).bind(AuthRepository::class)
}