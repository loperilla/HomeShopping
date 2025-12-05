package io.loperilla.register.presentation

import io.loperilla.domain.DoRegisterUseCase
import io.loperilla.domain.model.DomainError
import io.loperilla.domain.model.DomainResult
import io.loperilla.domain.model.auth.RegisterProvider
import io.loperilla.presentation.RegisterEvent
import io.loperilla.presentation.RegisterViewModel
import io.loperilla.testing.MainDispatcherRule
import io.loperilla.ui.navigator.Navigator
import io.loperilla.ui.navigator.routes.Destination
import io.loperilla.ui.snackbar.SnackbarController
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/*****
 * Project: HomeShopping
 * From: io.loperilla.register.presentation
 * Created By Manuel Lopera on 30/11/25 at 13:24
 * All rights reserved 2025
 */
@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: RegisterViewModel
    private val doRegisterUseCase: DoRegisterUseCase = mockk()
    private val navigator: Navigator = mockk(relaxed = true)
    private val snackbarController: SnackbarController = mockk(relaxed = true)

    @Before
    fun setUp() {
        viewModel = RegisterViewModel(doRegisterUseCase, navigator, snackbarController)
    }

    @Test
    fun `onEvent EmailValueChange updates state`() = runTest {
        val expectedEmail = "test@example.com"

        viewModel.onEvent(RegisterEvent.EmailValueChange(expectedEmail))

        assertEquals(expectedEmail, viewModel.stateFlow.value.email)
    }

    @Test
    fun `onEvent PasswordValueChange updates state`() = runTest {
        val expectedPassword = "password123"

        viewModel.onEvent(RegisterEvent.PasswordValueChange(expectedPassword))
        advanceUntilIdle()

        assertEquals(expectedPassword, viewModel.stateFlow.value.password)
    }

    @Test
    fun `onEvent RepeatPasswordValueChange updates state`() = runTest {
        val expectedRepeatPassword = "password123"

        viewModel.onEvent(RegisterEvent.RepeatPasswordValueChange(expectedRepeatPassword))
        advanceUntilIdle()

        assertEquals(expectedRepeatPassword, viewModel.stateFlow.value.confirmPassword)
    }

    @Test
    fun `onEvent OnBackPressed navigates up`() = runTest {
        viewModel.onEvent(RegisterEvent.OnBackPressed)
        advanceUntilIdle()

        coVerify { navigator.navigateUp() }
    }

    @Test
    fun `onEvent DoRegister with success navigates to Home`() = runTest {
        val email = "test@example.com"
        val password = "password123"

        // Preparamos el estado
        viewModel.onEvent(RegisterEvent.EmailValueChange(email))
        viewModel.onEvent(RegisterEvent.PasswordValueChange(password))
        advanceUntilIdle()

        // Mockeamos el caso de uso exitoso
        coEvery {
            doRegisterUseCase(RegisterProvider.MailPassword(email, password))
        } returns DomainResult.Success(Unit)

        // Ejecutamos la acción
        viewModel.onEvent(RegisterEvent.DoRegister)
        advanceUntilIdle()

        // Verificamos la navegación
        coVerify { navigator.navigate(Destination.Home) }
    }

    @Test
    fun `onEvent DoRegister with error shows snackbar`() = runTest {
        val email = "test@example.com"
        val password = "password123"
        val errorMessage = "Registration failed"

        // Preparamos el estado
        viewModel.onEvent(RegisterEvent.EmailValueChange(email))
        viewModel.onEvent(RegisterEvent.PasswordValueChange(password))
        advanceUntilIdle()

        // Mockeamos el caso de uso con error
        coEvery {
            doRegisterUseCase(RegisterProvider.MailPassword(email, password))
        } returns DomainResult.Error(DomainError.UnknownError(Throwable(errorMessage)))

        // Ejecutamos la acción
        viewModel.onEvent(RegisterEvent.DoRegister)
        advanceUntilIdle()

        // Verificamos que NO navega y SI muestra snackbar
        coVerify(exactly = 0) { navigator.navigate(any()) }
        coVerify {
            snackbarController.sendEvent(
                match { it.message == errorMessage }
            )
        }
    }

    @Test
    fun `onEvent DoGoogleRegister with success navigates to Home`() = runTest {
        // Mockeamos el caso de uso exitoso para Google
        coEvery {
            doRegisterUseCase(RegisterProvider.Google)
        } returns DomainResult.Success(Unit)

        viewModel.onEvent(RegisterEvent.DoGoogleRegister)
        advanceUntilIdle()

        coVerify { navigator.navigate(Destination.Home) }
    }

    @Test
    fun `onEvent DoGoogleRegister with error shows snackbar`() = runTest {
        val errorMessage = "Google sign in failed"

        // Mockeamos el caso de uso con error para Google
        coEvery {
            doRegisterUseCase(RegisterProvider.Google)
        } returns DomainResult.Error(DomainError.UnknownError(Throwable(errorMessage)))

        viewModel.onEvent(RegisterEvent.DoGoogleRegister)
        advanceUntilIdle()

        coVerify(exactly = 0) { navigator.navigate(any()) }
        coVerify {
            snackbarController.sendEvent(
                match { it.message == errorMessage }
            )
        }
    }
}