package io.loperilla.login.presentation.test

import io.loperilla.domain.model.DomainError
import io.loperilla.domain.model.DomainResult
import io.loperilla.domain.usecase.DoLoginUseCase
import io.loperilla.presentation.LoginEvent
import io.loperilla.presentation.LoginViewModel
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
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/*****
 * Project: HomeShopping
 * From: io.loperilla.login.presentation.test
 * Created By Manuel Lopera on 30/11/25 at 10:37
 * All rights reserved 2025
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val doLoginUseCase: DoLoginUseCase = mockk()
    private val navigator: Navigator = mockk(relaxed = true)
    private val snackbarController: SnackbarController = mockk(relaxed = true)

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        viewModel = LoginViewModel(doLoginUseCase, navigator, snackbarController)
    }

    @Test
    fun `when EmailValueChange event is triggered, state should update email`() = runTest {
        // Given
        val newEmail = "test@example.com"

        // When
        viewModel.onEvent(LoginEvent.EmailValueChange(newEmail))

        // Then
        assertEquals(newEmail, viewModel.stateFlow.value.emailInputValue)
    }

    @Test
    fun `when PasswordValueChange event is triggered, state should update password`() = runTest {
        // Given
        val newPassword = "Password123!"

        // When
        viewModel.onEvent(LoginEvent.PasswordValueChange(newPassword))

        // Then
        assertEquals(newPassword, viewModel.stateFlow.value.passwordInputValue)
    }

    @Test
    fun `when RegisterButtonClicked event is triggered, should navigate to Register`() = runTest {
        // When
        viewModel.onEvent(LoginEvent.RegisterButtonClicked)

        // Then
        coVerify { navigator.navigate(Destination.Register) }
    }

    @Test
    fun `when LoginButtonClicked event is triggered and login is successful, should navigate to Home`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "Password123!"

        // Simulamos el input del usuario primero
        viewModel.onEvent(LoginEvent.EmailValueChange(email))
        viewModel.onEvent(LoginEvent.PasswordValueChange(password))

        // Mockeamos el caso de éxito del UseCase
        coEvery { doLoginUseCase(email, password) } returns DomainResult.Success(Unit)

        // When
        viewModel.onEvent(LoginEvent.LoginButtonClicked)
        advanceUntilIdle()

        // Then
        coVerify { navigator.navigate(Destination.Home) }
    }

    @Test
    fun `when LoginButtonClicked event is triggered and login fails, should show snackbar`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "WrongPassword"
        val errorMessage = "Credenciales inválidas"
        val throwable = Throwable(errorMessage)

        viewModel.onEvent(LoginEvent.EmailValueChange(email))
        viewModel.onEvent(LoginEvent.PasswordValueChange(password))

        coEvery { doLoginUseCase(email, password) } returns DomainResult.Error(DomainError.UnknownError(throwable))

        // When
        viewModel.onEvent(LoginEvent.LoginButtonClicked)
        advanceUntilIdle()

        // Then
        coVerify {
            snackbarController.sendEvent(match { event ->
                event.message == errorMessage
            })
        }
    }

    @Test
    fun `isValidForm returns true when email and password are valid`() = runTest {
        // Given
        val validEmail = "test@valid.com"
        val validPassword = "ValidPass123"

        // When
        viewModel.onEvent(LoginEvent.EmailValueChange(validEmail))
        viewModel.onEvent(LoginEvent.PasswordValueChange(validPassword))

        // Then
        assertTrue(viewModel.stateFlow.value.isValidForm)
    }
}