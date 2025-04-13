package io.loperilla.presentation.di

import io.loperilla.presentation.CommerceViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/*****
 * Project: HomeShopping
 * From: io.loperilla.presentation.di
 * Created By Manuel Lopera on 16/3/25 at 12:38
 * All rights reserved 2025
 */

val commercePresentationModule = module{
    viewModelOf(::CommerceViewModel)
}