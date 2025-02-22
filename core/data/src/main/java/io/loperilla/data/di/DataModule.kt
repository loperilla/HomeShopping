package io.loperilla.data.di

import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.loperilla.data.local.database.HomeShoppingDatabase
import io.loperilla.data.local.database.dao.QueryDao
import io.loperilla.data.local.database.dao.UserDao
import io.loperilla.data.network.AuthRepositoryImpl
import io.loperilla.data.network.SHOPPING_LIST_COLLECTION_NAME
import io.loperilla.data.network.ShoppingListCollection
import io.loperilla.data.network.ShoppingListRepositoryImpl
import io.loperilla.data.repository.LocalDataRepositoryImpl
import io.loperilla.domain.repository.AuthRepository
import io.loperilla.domain.repository.LocalDataRepository
import io.loperilla.domain.repository.ShoppingListRepository
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
    includes(databaseModule)
    includes(authModule)
    includes(firestoreModule)
    includes(repositoryModule)
}

private val repositoryModule = module {
    singleOf(::LocalDataRepositoryImpl).bind(LocalDataRepository::class)
}

private val firestoreModule = module {
    single<FirebaseFirestore> { Firebase.firestore }
    single<ShoppingListCollection> { get<FirebaseFirestore>().collection(SHOPPING_LIST_COLLECTION_NAME) }
    singleOf(::ShoppingListRepositoryImpl).bind(ShoppingListRepository::class)
}

private val authModule = module {
    single<FirebaseAuth> { Firebase.auth }
    singleOf(::AuthRepositoryImpl).bind(AuthRepository::class)
}

private val databaseModule = module {
    single<HomeShoppingDatabase> {
        Room
            .databaseBuilder(
                androidContext(),
                HomeShoppingDatabase::class.java,
                HomeShoppingDatabase::class.java.simpleName
            ).build()
    }
    factory<QueryDao> { get<HomeShoppingDatabase>().queryDao() }
    factory<UserDao> { get<HomeShoppingDatabase>().userDao() }
}