package io.loperilla.commerce.domain.test

import io.loperilla.domain.NewCommerceUseCase
import io.loperilla.domain.di.commerceDomainModule
import io.loperilla.domain.model.DomainError
import io.loperilla.domain.model.DomainResult
import io.loperilla.domain.model.commerce.CommerceDto
import io.loperilla.domain.repository.AuthRepository
import io.loperilla.domain.repository.CommerceRepository
import io.loperilla.domain.repository.LocalDataRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.KoinTest
import org.koin.test.verify.verify
import kotlin.test.assertEquals
import kotlin.test.assertTrue


/*****
 * Project: HomeShopping
 * From: io.loperilla.commerce.domain.test
 * Created By Manuel Lopera on 30/11/25 at 12:49
 * All rights reserved 2025
 */
@OptIn(KoinExperimentalAPI::class)
class NewCommerceUseCaseTest: KoinTest {

    private val repository: CommerceRepository = mockk()
    private lateinit var newCommerceUseCase: NewCommerceUseCase

    @Before
    fun setUp() {
        newCommerceUseCase = NewCommerceUseCase(repository)
    }

    @Test
    fun `check commerce module configuration`() {
        val extraTypes = listOf(
            CommerceRepository::class,
        )

        commerceDomainModule.verify(extraTypes = extraTypes)
    }

    @Test
    fun `WHEN invoke is called THEN calls repository with correct DTO`() = runTest {
        // GIVEN
        val commerceName = "Mi Nueva Tienda"
        val expectedDto = CommerceDto(commerceName = commerceName)

        // Simulamos que el repositorio devuelve Success
        coEvery { repository.addCommerce(any()) } returns DomainResult.Success(Unit)

        // WHEN
        val result = newCommerceUseCase(commerceName)

        // THEN
        // 1. Verificamos que sea un éxito
        assertTrue(result is DomainResult.Success)

        // 2. Verificamos que el repositorio se llamó con el DTO correcto
        coVerify(exactly = 1) {
            repository.addCommerce(match { it.commerceName == commerceName })
        }
    }

    @Test
    fun `WHEN repository fails THEN use case returns error`() = runTest {
        // GIVEN
        val commerceName = "Tienda Error"
        val expectedError = DomainError.UnknownError(Throwable("DB Error"))

        // Simulamos error en el repositorio
        coEvery { repository.addCommerce(any()) } returns DomainResult.Error(expectedError)

        // WHEN
        val result = newCommerceUseCase(commerceName)

        // THEN
        assertTrue(result is DomainResult.Error)
        assertEquals(expectedError, (result as DomainResult.Error).error)

        coVerify(exactly = 1) { repository.addCommerce(any()) }
    }
}