package io.loperilla.data.di

import androidx.credentials.CredentialManager
import androidx.room.Room
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import io.loperilla.data.local.account.AccountManagerImpl
import io.loperilla.data.local.database.HomeShoppingDatabase
import io.loperilla.data.local.database.dao.QueryDao
import io.loperilla.data.local.database.dao.UserDao
import io.loperilla.data.network.AuthRepositoryImpl
import io.loperilla.data.network.COMMERCE_COLLECTION_NAME
import io.loperilla.data.network.PRODUCTS_COLLECTION_NAME
import io.loperilla.data.network.SHOPPING_LIST_COLLECTION_NAME
import io.loperilla.data.repository.CommerceRepositoryImpl
import io.loperilla.data.repository.LocalDataRepositoryImpl
import io.loperilla.data.repository.ProductsRepositoryImpl
import io.loperilla.data.repository.ShoppingListRepositoryImpl
import io.loperilla.domain.repository.AccountManager
import io.loperilla.domain.repository.AuthRepository
import io.loperilla.domain.repository.CommerceRepository
import io.loperilla.domain.repository.LocalDataRepository
import io.loperilla.domain.repository.ProductsRepository
import io.loperilla.domain.repository.ShoppingListRepository
import org.koin.android.ext.koin.androidApplication
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
    factory<UserDao> { get<HomeShoppingDatabase>().userDao() }

    singleOf(::LocalDataRepositoryImpl).bind(LocalDataRepository::class)

    single<FirebaseFirestore> { Firebase.firestore }
    single<ShoppingListRepository> {
        val collection = get<FirebaseFirestore>().collection(SHOPPING_LIST_COLLECTION_NAME)
        ShoppingListRepositoryImpl(collection)
    }


    single<CommerceRepository> {
        val commerceCollection = get<FirebaseFirestore>().collection(COMMERCE_COLLECTION_NAME)
        CommerceRepositoryImpl(commerceCollection)
    }

    single<ProductsRepository> {
        val productCollection = get<FirebaseFirestore>().collection(PRODUCTS_COLLECTION_NAME)
        ProductsRepositoryImpl(productCollection)
    }

    // Auth
    single<SignInClient> { Identity.getSignInClient(androidApplication()) }
    single<CredentialManager> { CredentialManager.create(androidContext()) }
    singleOf(::AccountManagerImpl).bind(AccountManager::class)
    
    single<FirebaseAuth> { Firebase.auth }
    singleOf(::AuthRepositoryImpl).bind(AuthRepository::class)
}
