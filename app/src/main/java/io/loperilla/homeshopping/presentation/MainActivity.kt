package io.loperilla.homeshopping.presentation

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.loperilla.designsystem.composables.Screen
import io.loperilla.homeshopping.AppNavigation
import io.loperilla.ui.navigator.Navigator
import io.loperilla.ui.snackbar.SnackbarController
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

/*****
 * Project: HomeShopping
 * From: io.loperilla.homeshopping
 * Created By Manuel Lopera on 21/8/23 at 17:07
 * All rights reserved 2023
 */

class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition {
            viewModel.stateFlow.value == MainActivityUiState.Loading
        }
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
            val snackbarController = get<SnackbarController>()
            val navigator = get<Navigator>()
            val state by viewModel.stateFlow.collectAsStateWithLifecycle()
            when(val currentState = state) {
                MainActivityUiState.Loading -> Unit
                is MainActivityUiState.Success -> {
                    Screen {
                        AppNavigation(
                            navigator,
                            snackbarController,
                            currentState.goToWelcome
                        )
                    }
                }
            }
        }
    }
}