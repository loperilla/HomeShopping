package io.loperilla.presentation

import io.loperilla.domain.model.DomainError
import io.loperilla.domain.model.DomainResult
import io.loperilla.domain.model.auth.UserUpdateRequest
import io.loperilla.domain.usecase.auth.GetCurrentUserUseCase
import io.loperilla.domain.usecase.auth.UpdateUserUseCase
import io.loperilla.testing.MainDispatcherRule
import io.loperilla.testing.mock.emptyUserMock
import io.loperilla.ui.navigator.Navigator
import io.loperilla.ui.snackbar.SnackbarController
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/*****
 * Project: HomeShopping
 * From: io.loperilla.presentation
 * Created By Manuel Lopera on 5/12/25 at 18:40
 * All rights reserved 2025
 */
@OptIn(ExperimentalCoroutinesApi::class)
class UserDetailViewModelTest {
    @get:Rule
    val dispatcher = MainDispatcherRule()
    private val getCurrentUserUseCase: GetCurrentUserUseCase = mockk()
    private val updateUserUseCase: UpdateUserUseCase = mockk()
    private val navigator: Navigator = mockk(relaxUnitFun = true)
    private val snackbarController: SnackbarController = mockk(relaxUnitFun = true)
    private lateinit var viewModel: UserDetailViewModel

    @Before
    fun setUp() {
        viewModel = UserDetailViewModel(
            getCurrentUserUseCase,
            updateUserUseCase,
            navigator,
            snackbarController
        )
    }

    @Test
    fun `when flow is collected and load fails, shows error snackbar`() = runTest {
        // Given
        val error = DomainError.UnknownError()
        coEvery { getCurrentUserUseCase() } returns DomainResult.Error(error)

        // When
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.stateFlow.collect()
        }
        advanceUntilIdle()

        // Then
        coVerify {
            snackbarController.sendEvent(any())
        }

        collectJob.cancel()
    }

    @Test
    fun `when flow is collected, loads current user successfully and updates state`() = runTest {
        // Given
        coEvery { getCurrentUserUseCase() } returns DomainResult.Success(emptyUserMock)

        // When
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.stateFlow.collect()
        }
        advanceUntilIdle()

        // Then
        val currentState = viewModel.stateFlow.value
        assertEquals(emptyUserMock, currentState.user)
        assertFalse(currentState.isLoading)

        collectJob.cancel()
    }

    @Test
    fun `onEvent OnNameChanged updates state correctly`() = runTest {
        // Given
        val newName = "Manuel Updated"
        coEvery { getCurrentUserUseCase() } returns DomainResult.Success(emptyUserMock.copy(name = newName))

        // When
        viewModel.onEvent(UserDetailEvent.OnNameChanged(newName))

        // Then
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.stateFlow.collect()
        }
        advanceUntilIdle()

        assertEquals(newName, viewModel.stateFlow.value.newUserName)

        collectJob.cancel()
    }

    @Test
    fun `onEvent SaveButtonClick calls update use case and shows success snackbar`() = runTest {
        val newName = "New Name"
        viewModel.onEvent(UserDetailEvent.OnNameChanged(newName))
        coEvery { getCurrentUserUseCase() } returns DomainResult.Success(emptyUserMock.copy(name = newName))
        coEvery { updateUserUseCase(UserUpdateRequest(displayName = newName)) } returns DomainResult.Success(Unit)

        // When
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.stateFlow.collect()
        }
        viewModel.onEvent(UserDetailEvent.SaveButtonClick)
        advanceUntilIdle()

        coVerify {
            snackbarController.sendEvent(any())
        }
        collectJob.cancel()
    }

    @Test
    fun `onEvent SaveButtonClick shows error snackbar when update fails`() = runTest {
        // Given
        coEvery { updateUserUseCase(any()) } returns DomainResult.Error(DomainError.UnknownError())

        // When
        viewModel.onEvent(UserDetailEvent.SaveButtonClick)
        advanceUntilIdle()

        // Then
        coVerify {
            snackbarController.sendEvent(match { it.message == "Hubo un error al actualizar el usuario" })
        }
    }

    @Test
    fun `onEvent OnBackPressed navigates up`() = runTest {
        // When
        viewModel.onEvent(UserDetailEvent.OnBackPressed)
        advanceUntilIdle()

        // Then
        coVerify { navigator.navigateUp() }
    }
}