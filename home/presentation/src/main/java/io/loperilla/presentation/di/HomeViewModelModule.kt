package io.loperilla.presentation.di

import io.loperilla.presentation.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/*****
 * Project: HomeShopping
 * From: io.loperilla.presentation.di
 * Created By Manuel Lopera on 19/2/25 at 20:00
 * All rights reserved 2025
 */

val homeViewModelModule = module {
    viewModelOf(::HomeViewModel)
}