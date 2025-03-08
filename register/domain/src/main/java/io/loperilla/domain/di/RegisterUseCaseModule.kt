package io.loperilla.domain.di

import io.loperilla.domain.DoRegisterUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/*****
 * Project: HomeShopping
 * From: io.loperilla.domain.di
 * Created By Manuel Lopera on 22/2/25 at 10:59
 * All rights reserved 2025
 */

val registerUseCaseModule = module {
    singleOf(::DoRegisterUseCase)
}
