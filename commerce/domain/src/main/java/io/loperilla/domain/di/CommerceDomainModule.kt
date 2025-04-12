package io.loperilla.domain.di

import io.loperilla.domain.GetCommercesUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/*****
 * Project: HomeShopping
 * From: io.loperilla.domain.di
 * Created By Manuel Lopera on 12/4/25 at 16:32
 * All rights reserved 2025
 */

val commerceDomainModule = module {
    singleOf(::GetCommercesUseCase)
}