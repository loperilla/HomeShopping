package io.loperilla.homeshopping.di

import io.loperilla.homeshopping.presentation.MainActivityViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/*****
 * Project: HomeShopping
 * From: io.loperilla.homeshopping.di
 * Created By Manuel Lopera on 29/11/25 at 10:30
 * All rights reserved 2025
 */

val mainActivityPresentationModule = module {
    viewModelOf(::MainActivityViewModel)
}