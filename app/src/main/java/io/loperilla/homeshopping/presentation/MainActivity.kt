package io.loperilla.homeshopping.presentation

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.loperilla.designsystem.composables.Screen
import io.loperilla.homeshopping.AppNavigation
import io.loperilla.ui.navigator.Navigator
import io.loperilla.ui.snackbar.SnackbarController
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.koinViewModel

/*****
 * Project: HomeShopping
 * From: io.loperilla.homeshopping
 * Created By Manuel Lopera on 21/8/23 at 17:07
 * All rights reserved 2023
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            ), navigationBarStyle = SystemBarStyle.auto(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        )
        super.onCreate(savedInstanceState)
        setContent {
            val navigator : Navigator by inject()
            val snackbarController : SnackbarController by inject()
            val viewModel = koinViewModel<MainActivityViewModel>()
            val state = viewModel.stateFlow.collectAsStateWithLifecycle()
            splashScreen.setKeepOnScreenCondition { state.value is MainActivityUiState.Loading }
            if (state.value is MainActivityUiState.Success) {
                Screen {
                    AppNavigation(
                        navigator,
                        snackbarController,
                    )
                }
            }
        }
    }
}