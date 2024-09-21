package io.loperilla.onboarding_domain.usecase.auth

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding_domain.usecase.auth
 * Created By Manuel Lopera on 7/9/24 at 18:48
 * All rights reserved 2024
 */
import assertk.assertThat
import assertk.assertions.isEqualTo
import io.loperilla.onboarding_domain.repository.AuthRepository
import io.loperilla.testutils.validEmail
import io.loperilla.testutils.validPassword
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RegisterUseCaseTest {
    @RelaxedMockK
    private lateinit var repository: AuthRepository

    private lateinit var registerUseCase: RegisterUseCase

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        registerUseCase = RegisterUseCase(repository)
    }

    @Test
    fun `test register use case success`() = runTest {
        // Arrange
        val email = validEmail
        val password = validPassword
        coEvery { repository.doRegister(email, password) } returns Result.success(Unit)

        // Act
        val result = registerUseCase(email, password)

        // Assert
        assertThat(result).isEqualTo(Result.success(Unit))
    }

    @Test
    fun `test register use case failure`() = runTest {
        // Arrange
        val email = validEmail
        val password = validPassword
        val exception = Exception("Test exception")
        coEvery { repository.doRegister(email, password) } returns Result.failure(exception)

        // Act
        val result = registerUseCase(email, password)

        // Assert
        assertThat(result).isEqualTo(Result.failure(exception))
    }
}