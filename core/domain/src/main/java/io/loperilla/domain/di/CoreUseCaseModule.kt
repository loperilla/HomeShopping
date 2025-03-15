package io.loperilla.domain.di

import io.loperilla.domain.usecase.auth.GetCurrentUserUseCase
import io.loperilla.domain.usecase.auth.LogOutUseCase
import io.loperilla.domain.usecase.auth.RefreshUserUseCase
import io.loperilla.domain.usecase.auth.UpdateUserUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

/*****
 * Project: HomeShopping
 * From: io.loperilla.domain.di
 * Created By Manuel Lopera on 19/2/25 at 20:10
 * All rights reserved 2025
 */

val coreUseCaseModule = module {
    factoryOf(::LogOutUseCase)
    factoryOf(::GetCurrentUserUseCase)
    factoryOf(::RefreshUserUseCase)
    factoryOf(::UpdateUserUseCase)
}