package io.loperilla.data.di

import io.loperilla.data.LoginRepositoryImpl
import io.loperilla.domain.usecase.LoginRepository
import org.koin.dsl.module

/*****
 * Project: HomeShopping
 * From: io.loperilla.data.di
 * Created By Manuel Lopera on 2/2/25 at 18:56
 * All rights reserved 2025
 */

val loginDataModule = module {
    single <LoginRepository> { LoginRepositoryImpl(get()) }
}