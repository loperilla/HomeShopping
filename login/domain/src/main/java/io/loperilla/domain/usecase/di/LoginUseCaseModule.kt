package io.loperilla.domain.usecase.di

/*****
 * Project: HomeShopping
 * From: io.loperilla.domain.usecase.di
 * Created By Manuel Lopera on 22/2/25 at 10:57
 * All rights reserved 2025
 */

import io.loperilla.domain.usecase.DoLoginUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val loginUseCaseModule = module {
    singleOf(::DoLoginUseCase)
}