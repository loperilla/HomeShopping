package io.loperilla.homeshopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.loperilla.core_ui.CompraCasaTheme
import io.loperilla.core_ui.LOW
import io.loperilla.core_ui.routes.Routes
import io.loperilla.model.auth.AuthResult
import io.loperilla.onboarding.home.HomeScreen
import io.loperilla.onboarding.login.LoginScreen
import io.loperilla.onboarding.login.LoginViewModel
import io.loperilla.onboarding.register.RegisterScreen
import io.loperilla.onboarding.register.RegisterViewModel

/*****
 * Project: HomeShopping
 * From: io.loperilla.homeshopping
 * Created By Manuel Lopera on 21/8/23 at 17:07
 * All rights reserved 2023
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompraCasaTheme {
                // A surface container using the 'background' color from the theme
                val navController: NavHostController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(LOW),
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(navBackStackEntry?.destination?.route ?: "")
                            }
                        )
                    }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = Routes.LOGIN
                    ) {
                        composable(Routes.LOGIN) {
                            val loginViewModel: LoginViewModel = hiltViewModel()
                            val emailValue by loginViewModel.emailInputValue.collectAsStateWithLifecycle()
                            val passwordValue by loginViewModel.passwordInputValue.collectAsStateWithLifecycle()
                            val requestState: AuthResult by loginViewModel.loginRequestState.collectAsStateWithLifecycle()

                            when (requestState) {
                                AuthResult.AuthSuccess -> navController.navigate(Routes.HOME)
                                AuthResult.LoadingRequest -> {
                                    Column(
                                        modifier = Modifier
                                            .padding(paddingValues)
                                    ) {
                                        CircularProgressIndicator(
                                            modifier = Modifier
                                                .align(Alignment.CenterHorizontally)
                                        )
                                    }
                                }

                                else -> {
                                    if (requestState != AuthResult.AuthNone) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(paddingValues)
                                                .background(Color.Red)
                                        ) {
                                            Text("Falló el registro")
                                        }
                                    }
                                    LoginScreen(
                                        emailValue,
                                        passwordValue,
                                        onEmailChange = loginViewModel::emailValueChange,
                                        onPasswordChange = loginViewModel::passwordValueChange,
                                        modifier = Modifier
                                            .padding(paddingValues),
                                        loginButtonClicked = loginViewModel::loginButtonClicked
                                    ) {
                                        navController.navigate(Routes.REGISTER)
                                    }
                                }
                            }
                        }

                        composable(Routes.REGISTER) {
                            val registerViewModel: RegisterViewModel = hiltViewModel()
                            val emailValue by registerViewModel.emailInputValue.collectAsStateWithLifecycle()
                            val passwordValue by registerViewModel.passwordInputValue.collectAsStateWithLifecycle()
                            val authState by registerViewModel.authState.collectAsStateWithLifecycle()

                            when (authState) {
                                AuthResult.AuthSuccess -> navController.navigate(Routes.HOME)
                                AuthResult.LoadingRequest -> {
                                    Column(
                                        modifier = Modifier
                                            .padding(paddingValues)
                                    ) {
                                        CircularProgressIndicator(
                                            modifier = Modifier
                                                .align(Alignment.CenterHorizontally)
                                        )
                                    }
                                }

                                else -> {
                                    if (authState != AuthResult.AuthNone) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(paddingValues)
                                                .background(Color.Red)
                                        ) {
                                            Text("Falló el registro")
                                        }
                                    }
                                    RegisterScreen(
                                        emailValue,
                                        passwordValue,
                                        onEmailChange = registerViewModel::emailValueChange,
                                        onPasswordChange = registerViewModel::passwordValueChange,
                                        modifier = Modifier
                                            .padding(paddingValues),
                                        registerButtonClicked = registerViewModel::doRegister
                                    )
                                }
                            }
                        }

                        composable(Routes.HOME) {
                            HomeScreen()
                        }
                    }
                }
            }
        }
    }
}
