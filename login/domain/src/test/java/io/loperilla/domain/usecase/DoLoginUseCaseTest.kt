package io.loperilla.domain.usecase

import io.loperilla.domain.model.DomainError
import io.loperilla.domain.model.DomainResult
import io.loperilla.domain.model.auth.User
import io.loperilla.domain.repository.AuthRepository
import io.loperilla.domain.repository.LocalDataRepository
import io.loperilla.domain.usecase.di.loginUseCaseModule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.verify.verify
import kotlin.test.assertEquals

/*****
 * Project: HomeShopping
 * From: io.loperilla.domain.usecase
 * Created By Manuel Lopera on 30/11/25 at 11:19
 * All rights reserved 2025
 */

class LoginUseCaseTest : KoinTest {

    private val authRepository: AuthRepository = mockk()
    private val localDataRepository: LocalDataRepository = mockk()

    private lateinit var doLoginUseCase: DoLoginUseCase

    @Before
    fun setUp() {
        doLoginUseCase = DoLoginUseCase(authRepository, localDataRepository)
    }

    @Test
    fun `check login module configuration`() {
        val extraTypes = listOf(
            AuthRepository::class,
            LocalDataRepository::class
        )

        loginUseCaseModule.verify(extraTypes = extraTypes)
    }

    @Test
    fun `when auth is successful, should persist user and return Success`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password123"
        val user = User(uid = "123", name = "Test User", email = email, photoUrl = "")

        coEvery { authRepository.doLogin(email, password) } returns DomainResult.Success(user)
        coEvery { localDataRepository.persistUser(user) } returns DomainResult.Success(Unit)

        // When
        val result = doLoginUseCase(email, password)

        // Then
        assertTrue(result is DomainResult.Success)

        coVerify(exactly = 1) { localDataRepository.persistUser(user) }
    }

    @Test
    fun `when auth fails, should NOT persist user and return Error`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "wrongPassword"
        val errorMessage = "Login failed"
        val throwable = Throwable(errorMessage)
        val expectedError = DomainError.UnknownError(throwable)

        coEvery { authRepository.doLogin(email, password) } returns DomainResult.Error(expectedError)

        // When
        val result = doLoginUseCase(email, password)

        // Then
        assertTrue(result is DomainResult.Error)
        assertEquals(expectedError, (result as DomainResult.Error).error)

        coVerify(exactly = 0) { localDataRepository.persistUser(any()) }
    }
}