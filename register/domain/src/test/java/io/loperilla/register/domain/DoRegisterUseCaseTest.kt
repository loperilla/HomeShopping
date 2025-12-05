package io.loperilla.register.domain

import io.loperilla.domain.DoRegisterUseCase
import io.loperilla.domain.model.DomainError
import io.loperilla.domain.model.DomainResult
import io.loperilla.domain.model.auth.RegisterProvider
import io.loperilla.domain.model.auth.User
import io.loperilla.domain.repository.AuthRepository
import io.loperilla.domain.repository.LocalDataRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/*****
 * Project: HomeShopping
 * From: io.loperilla.register.domain
 * Created By Manuel Lopera on 5/12/25 at 17:07
 * All rights reserved 2025
 */
class DoRegisterUseCaseTest {
    private val authRepository: AuthRepository = mockk()
    private val localDataRepository: LocalDataRepository = mockk(relaxUnitFun = true)
    private lateinit var doRegisterUseCase: DoRegisterUseCase

    @Before
    fun setUp() {
        doRegisterUseCase = DoRegisterUseCase(authRepository, localDataRepository)
    }

    @Test
    fun `invoke returns Success when registration and persistence are successful`() = runTest {
        // Given
        val registerProvider = RegisterProvider.MailPassword("dummy", "dummy")
        val user = User("", "", "", "")

        // Simulamos que el repositorio de auth responde con éxito devolviendo un User
        coEvery { authRepository.doRegister(registerProvider) } returns DomainResult.Success(user)
        coEvery { localDataRepository.persistUser(user) } returns DomainResult.Success(Unit)

        val result = doRegisterUseCase(registerProvider)

        assertTrue(result is DomainResult.Success)

        coVerify(exactly = 1) { localDataRepository.persistUser(user) }
    }

    @Test
    fun `invoke returns Error when registration fails`() = runTest {
        val registerProvider = RegisterProvider.MailPassword("dummy", "dummy")
        val expectedError = DomainError.UnknownError()

        coEvery { authRepository.doRegister(registerProvider) } returns DomainResult.Error(expectedError)

        val result = doRegisterUseCase(registerProvider)

        assertTrue(result is DomainResult.Error)
        assertEquals(expectedError, (result as DomainResult.Error).error)

        coVerify(exactly = 0) { localDataRepository.persistUser(any()) }
    }

}