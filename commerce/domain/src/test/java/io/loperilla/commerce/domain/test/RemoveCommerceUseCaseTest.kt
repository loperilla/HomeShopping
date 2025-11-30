package io.loperilla.commerce.domain.test

import io.loperilla.domain.RemoveCommerceUseCase
import io.loperilla.domain.di.commerceDomainModule
import io.loperilla.domain.model.DomainError
import io.loperilla.domain.model.DomainResult
import io.loperilla.domain.repository.CommerceRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.verify.verify
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/*****
 * Project: HomeShopping
 * From: io.loperilla.commerce.domain.test
 * Created By Manuel Lopera on 30/11/25 at 12:55
 * All rights reserved 2025
 */
@OptIn(KoinExperimentalAPI::class)
class RemoveCommerceUseCaseTest {

    private val repository: CommerceRepository = mockk()
    private lateinit var removeCommerceUseCase: RemoveCommerceUseCase

    @Before
    fun setUp() {
        removeCommerceUseCase = RemoveCommerceUseCase(repository)
    }

    @Test
    fun `check commerce module configuration`() {
        val extraTypes = listOf(
            CommerceRepository::class,
        )

        commerceDomainModule.verify(extraTypes = extraTypes)
    }

    @Test
    fun `WHEN invoke is called THEN calls repository remove with correct ID`() = runTest {
        // GIVEN
        val commerceId = "12345"

        // Simulamos éxito en el repositorio
        coEvery { repository.removeCommerce(commerceId) } returns DomainResult.Success(Unit)

        // WHEN
        val result = removeCommerceUseCase(commerceId)

        // THEN
        // 1. Verificamos que sea Success
        assertTrue(result is DomainResult.Success)

        // 2. Verificamos que se llamó al repositorio con el ID exacto
        coVerify(exactly = 1) {
            repository.removeCommerce(commerceId)
        }
    }

    @Test
    fun `WHEN repository fails THEN use case returns error`() = runTest {
        // GIVEN
        val commerceId = "999"
        val expectedError = DomainError.UnknownError(Throwable("Cannot delete"))

        // Simulamos error en el repositorio
        coEvery { repository.removeCommerce(commerceId) } returns DomainResult.Error(expectedError)

        // WHEN
        val result = removeCommerceUseCase(commerceId)

        // THEN
        // 1. Verificamos que sea Error
        assertTrue(result is DomainResult.Error)

        // 2. Verificamos que el error sea el esperado
        assertEquals(expectedError, (result as DomainResult.Error).error)

        coVerify(exactly = 1) { repository.removeCommerce(commerceId) }
    }
}