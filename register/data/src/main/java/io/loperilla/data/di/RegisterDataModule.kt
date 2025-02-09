package io.loperilla.data.di

import io.loperilla.data.RegisterRepositoryImpl
import io.loperilla.domain.RegisterRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/*****
 * Project: HomeShopping
 * From: io.loperilla.data.di
 * Created By Manuel Lopera on 9/2/25 at 13:30
 * All rights reserved 2025
 */

val registerDataModule = module {
    singleOf(::RegisterRepositoryImpl).bind(RegisterRepository::class)
}