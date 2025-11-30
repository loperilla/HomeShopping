package io.loperilla.commerce.presentation

import io.loperilla.domain.NewCommerceUseCase
import io.loperilla.domain.RemoveCommerceUseCase
import io.loperilla.domain.model.DomainError
import io.loperilla.domain.model.DomainResult
import io.loperilla.domain.model.commerce.Commerce
import io.loperilla.domain.usecase.commerce.GetCommercesUseCase
import io.loperilla.presentation.CommerceEvent
import io.loperilla.presentation.CommerceViewModel
import io.loperilla.testing.MainDispatcherRule
import io.loperilla.ui.navigator.Navigator
import io.loperilla.ui.snackbar.SnackbarController
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/*****
 * Project: HomeShopping
 * From: io.loperilla.presentation.commerce
 * Created By Manuel Lopera on 30/11/25 at 11:49
 * All rights reserved 2025
 */

@OptIn(ExperimentalCoroutinesApi::class)
class CommerceViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val navigator: Navigator = mockk(relaxed = true)
    private val snackbarController = mockk<SnackbarController>(relaxed = true)
    private val getCommercesUseCase: GetCommercesUseCase = mockk()
    private val newCommerceUseCase: NewCommerceUseCase = mockk()
    private val removeCommerceUseCase: RemoveCommerceUseCase = mockk()

    private lateinit var viewModel: CommerceViewModel

    @Before
    fun setUp() {
        viewModel = CommerceViewModel(
            navigator,
            getCommercesUseCase,
            newCommerceUseCase,
            removeCommerceUseCase,
            snackbarController
        )
    }

    @Test
    fun `WHEN init viewModel THEN getCommerceList is executed and state updated`() = runTest {
        // GIVEN
        val mockList = listOf<Commerce>()
        coEvery { getCommercesUseCase() } returns DomainResult.Success(mockList)

        // Iniciamos la recolección del flujo en segundo plano para disparar el onStart
        val job = launch { viewModel.stateFlow.collect {} }

        // WHEN
        // Esperamos a que las corrutinas pendientes terminen
        advanceUntilIdle()

        // THEN
        val currentState = viewModel.stateFlow.value
        assert(currentState.commerceList == mockList)
        assert(!currentState.isLoading)

        job.cancel()
    }
    @Test
    fun `WHEN init viewModel fails THEN isLoading becomes false`() = runTest {
        // GIVEN
        val errorMsg = "Error de red"
        val domainError = DomainError.UnknownError(Throwable(errorMsg))

        // Simulamos que la carga inicial falla
        coEvery { getCommercesUseCase() } returns DomainResult.Error(domainError)

        // Trigger del onStart
        val job = launch { viewModel.stateFlow.collect {} }
        advanceUntilIdle()

        // THEN
        val currentState = viewModel.stateFlow.value

        // El viewmodel pone isLoading = false en el onError
        assert(!currentState.isLoading)
        // La lista debería estar vacía (valor inicial)
        assert(currentState.commerceList.isEmpty())

        job.cancel()
    }

    @Test
    fun `WHEN GoBack event is triggered THEN navigator navigates up`() = runTest {
        // GIVEN
        // Mockeamos la carga inicial para que no falle el onStart
        coEvery { getCommercesUseCase() } returns DomainResult.Success(emptyList())

        // Activamos el flujo
        val job = launch { viewModel.stateFlow.collect {} }
        advanceUntilIdle()

        // WHEN
        viewModel.onEvent(CommerceEvent.GoBack)
        advanceUntilIdle()

        // THEN
        coVerify { navigator.navigateUp() }

        job.cancel()
    }

    @Test
    fun `WHEN AddNewCommerce event is triggered THEN showNewCommerceInput becomes true`() = runTest {
        // GIVEN
        coEvery { getCommercesUseCase() } returns DomainResult.Success(emptyList())
        val job = launch { viewModel.stateFlow.collect {} }
        advanceUntilIdle()

        // WHEN
        viewModel.onEvent(CommerceEvent.AddNewCommerce)
        advanceUntilIdle() // Dejamos que el estado se actualice

        // THEN
        assert(viewModel.stateFlow.value.showNewCommerceInput)

        job.cancel()
    }

    @Test
    fun `WHEN NewCommerceNameChanged event is triggered THEN name is updated`() = runTest {
        // GIVEN
        coEvery { getCommercesUseCase() } returns DomainResult.Success(emptyList())
        val job = launch { viewModel.stateFlow.collect {} }
        advanceUntilIdle()

        val newName = "Mi Tienda"

        // WHEN
        viewModel.onEvent(CommerceEvent.NewCommerceNameChanged(newName))
        advanceUntilIdle()

        // THEN
        assert(viewModel.stateFlow.value.newCommerceName == newName)

        job.cancel()
    }

    @Test
    fun `WHEN SendNewCommerce event is triggered success THEN loads list and hides input`() = runTest {
        // GIVEN
        val commerceName = "Nueva Tienda"
        coEvery { getCommercesUseCase() } returns DomainResult.Success(emptyList())
        coEvery { newCommerceUseCase(commerceName) } returns DomainResult.Success(Unit)

        val job = launch { viewModel.stateFlow.collect {} }
        advanceUntilIdle()

        viewModel.onEvent(CommerceEvent.NewCommerceNameChanged(commerceName))
        advanceUntilIdle()

        // WHEN
        viewModel.onEvent(CommerceEvent.SendNewCommerce)
        advanceUntilIdle()

        // THEN
        val state = viewModel.stateFlow.value
        assert(!state.showNewCommerceInput)
        assert(!state.isLoading)

        // Verificamos flujo: crear -> recargar lista
        coVerify { newCommerceUseCase(commerceName) }
        // getCommercesUseCase se llama 2 veces: 1 en el init (onStart) y 1 tras crear
        coVerify(exactly = 2) { getCommercesUseCase() }

        job.cancel()
    }

    @Test
    fun `WHEN SendNewCommerce event fails THEN show snackbar and hide loading`() = runTest {
        // GIVEN
        val errorMsg = "Error al crear"
        val throwable = Throwable(errorMsg)
        val domainError = DomainError.UnknownError(throwable)

        coEvery { getCommercesUseCase() } returns DomainResult.Success(emptyList())
        coEvery { newCommerceUseCase(any()) } returns DomainResult.Error(domainError)

        val job = launch { viewModel.stateFlow.collect {} }
        advanceUntilIdle()

        // WHEN
        viewModel.onEvent(CommerceEvent.SendNewCommerce)
        advanceUntilIdle()

        // THEN
        val state = viewModel.stateFlow.value
        assert(!state.isLoading)

        coVerify {
            snackbarController.sendEvent(match { it.message == errorMsg })
        }

        job.cancel()
    }

    @Test
    fun `WHEN RemoveCommerce event is triggered success THEN refresh list`() = runTest {
        // GIVEN
        val idToRemove = "123"
        coEvery { getCommercesUseCase() } returns DomainResult.Success(emptyList())
        coEvery { removeCommerceUseCase(idToRemove) } returns DomainResult.Success(Unit)

        val job = launch { viewModel.stateFlow.collect {} }
        advanceUntilIdle()

        // WHEN
        viewModel.onEvent(CommerceEvent.RemoveCommerce(idToRemove))
        advanceUntilIdle()

        // THEN
        assert(!viewModel.stateFlow.value.showNewCommerceInput)

        coVerify { removeCommerceUseCase(idToRemove) }
        coVerify(exactly = 2) { getCommercesUseCase() }

        job.cancel()
    }


    @Test
    fun `WHEN RemoveCommerce event fails THEN show snackbar`() = runTest {
        // GIVEN
        val idToRemove = "123"
        val errorMsg = "No se pudo borrar"
        val domainError = DomainError.UnknownError(Throwable(errorMsg))

        // 1. La carga inicial funciona bien
        coEvery { getCommercesUseCase() } returns DomainResult.Success(emptyList())
        // 2. El borrado falla
        coEvery { removeCommerceUseCase(idToRemove) } returns DomainResult.Error(domainError)

        val job = launch { viewModel.stateFlow.collect {} }
        advanceUntilIdle()

        // WHEN
        viewModel.onEvent(CommerceEvent.RemoveCommerce(idToRemove))
        advanceUntilIdle()

        // THEN
        val state = viewModel.stateFlow.value
        assert(!state.isLoading)

        // Verificamos que se envía el error al SnackbarController
        coVerify {
            snackbarController.sendEvent(match { it.message == errorMsg })
        }

        job.cancel()
    }

}