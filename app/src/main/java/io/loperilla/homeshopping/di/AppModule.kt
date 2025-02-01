package io.loperilla.homeshopping.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.loperilla.onboarding.navigator.DefaultNavigator
import io.loperilla.onboarding.navigator.Navigator
import javax.inject.Singleton

/*****
 * Project: HomeShopping
 * From: io.loperilla.homeshopping.di
 * Created By Manuel Lopera on 21/9/24 at 12:41
 * All rights reserved 2024
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNavigator(): Navigator = DefaultNavigator()
}