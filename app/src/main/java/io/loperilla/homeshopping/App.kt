package io.loperilla.homeshopping

import android.app.Application
import com.google.firebase.FirebaseApp
import io.loperilla.data.di.dataModule
import io.loperilla.data.di.loginDataModule
import io.loperilla.data.di.registerDataModule
import io.loperilla.presentation.di.loginViewModelModule
import io.loperilla.presentation.di.registerViewModelModule
import io.loperilla.splash.presentation.di.welcomeViewModelModule
import io.loperilla.ui.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.Module

/*****
 * Project: HomeShopping
 * From: io.loperilla.homeshopping
 * Created By Manuel Lopera on 21/8/23 at 17:07
 * All rights reserved 2023
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@App)
            modules(
                welcomeViewModelModule
            )
            modules(coreModule())
            modules(loginModule())
            modules(registerModule())
        }
    }

    private fun coreModule(): List<Module> = listOf(dataModule, uiModule)
    private fun loginModule(): List<Module> = listOf(loginDataModule, loginViewModelModule)
    private fun registerModule(): List<Module> = listOf(registerDataModule, registerViewModelModule)
}
