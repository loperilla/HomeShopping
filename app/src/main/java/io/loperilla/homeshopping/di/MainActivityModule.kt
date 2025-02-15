package io.loperilla.homeshopping.di

import io.loperilla.homeshopping.MainActivityViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/*****
 * Project: HomeShopping
 * From: io.loperilla.homeshopping.di
 * Created By Manuel Lopera on 15/2/25 at 10:09
 * All rights reserved 2025
 */

val mainActivityModule = module {
    viewModelOf(::MainActivityViewModel)
}