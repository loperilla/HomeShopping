package io.loperilla.welcome.presentation.test

import io.loperilla.splash.presentation.WelcomeEvent
import io.loperilla.splash.presentation.WelcomeViewModel
import io.loperilla.testing.MainDispatcherRule
import io.loperilla.ui.navigator.Navigator
import io.loperilla.ui.navigator.routes.Destination
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/*****
 * Project: HomeShopping
 * From: io.loperilla.welcome.presentation.test
 * Created By Manuel Lopera on 29/11/25 at 12:47
 * All rights reserved 2025
 */
@OptIn(ExperimentalCoroutinesApi::class)
class WelcomeViewModelTest {

    // 1. Regla para que viewModelScope funcione en los tests
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    // 2. Mocks (Simulaciones)
    // Usamos 'relaxed = true' para que no falle si llamamos métodos no configurados
    private val navigator: Navigator = mockk(relaxed = true)

    // 3. La clase a testear
    private lateinit var viewModel: WelcomeViewModel

    @Before
    fun setUp() {
        // Inicializamos el ViewModel con el mock antes de cada test
        viewModel = WelcomeViewModel(navigator)
    }

    @Test
    fun `WHEN NavigateToLogin event is triggered THEN navigator navigates to Login destination`() = runTest {
        // GIVEN
        val event = WelcomeEvent.NavigateToLogin

        // WHEN
        viewModel.onEvent(event)

        // THEN
        // Verificamos que la función navigate del navigator haya sido llamada
        // con el argumento exacto Destination.Login
        coVerify { navigator.navigateTo(Destination.Login) }
    }

    @Test
    fun `WHEN NavigateToRegister event is triggered THEN navigator navigates to Register destination`() = runTest {
        // GIVEN
        val event = WelcomeEvent.NavigateToRegister

        // WHEN
        viewModel.onEvent(event)

        // THEN
        coVerify { navigator.navigateTo(Destination.Register) }
    }
}